package org.hungrytessy.indycarsuperfan.presentation.schedule.singlerace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.hungrytessy.indycarsuperfan.R
import org.hungrytessy.indycarsuperfan.common.L
import org.hungrytessy.indycarsuperfan.presentation.IndyFragment
import org.hungrytessy.indycarsuperfan.presentation.MainActivity
import org.hungrytessy.indycarsuperfan.databinding.FragmentSingleRaceScheduleBinding
import org.hungrytessy.indycarsuperfan.domain.model.Venue
import org.hungrytessy.indycarsuperfan.presentation.adapters.OnDriverClickListener

private const val ARG_RACE_ID = "race_id"

@AndroidEntryPoint
class SingleRaceScheduleFragment : IndyFragment(), OnDriverClickListener {
    private val viewModel: SingleRaceScheduleViewModel by viewModels()
    private var _binding: FragmentSingleRaceScheduleBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RaceWeekendScheduleAdapter
    private lateinit var pastWinnersAdapter: PastWinnersAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        arguments?.getString(ARG_RACE_ID)?.let { raceId -> viewModel.initialize(raceId) }
        _binding = FragmentSingleRaceScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initObservables()
    }

    private fun setBarText(venue: Venue) {
        venue.name?.let { name ->
            if (requireActivity() is MainActivity) {
                (requireActivity() as MainActivity).supportActionBar?.title = name
            }
        }
    }

    private fun initObservables() {
        viewModel.raceWeekend.observe(viewLifecycleOwner) { raceWeekend ->
            adapter = RaceWeekendScheduleAdapter(raceWeekend)
            binding.weekendScheduleListView.layoutManager = LinearLayoutManager(requireContext())
            binding.weekendScheduleListView.setHasFixedSize(true)
            binding.weekendScheduleListView.adapter = adapter

            binding.venueView.raceDate.text = raceWeekend.race?.getScheduledDateTimeFormatted()
            binding.venueView.trackImg.setImageResource(raceWeekend.trackDrawable)
            //binding.trackBigImg.setImageResource(raceWeekend.getTrackDrawable())
        }

        viewModel.venue.observe(viewLifecycleOwner) { venue ->
            setBarText(venue)
            binding.venueView.raceMiles.text = getString(R.string.x_meters, venue.length)
            binding.venueView.raceLocation.text = venue.city
            binding.venueView.raceName.text = venue.name
        }

        viewModel.pastWinners.observe(viewLifecycleOwner) { pastWinners ->
            // TODO remove debug code
            for (key in pastWinners.keys) {
                L(key, pastWinners[key]?.name)
            }
            pastWinnersAdapter = PastWinnersAdapter(pastWinners, this)
            binding.weekendSchedulePastWinnersListView.layoutManager = LinearLayoutManager(requireContext())
            binding.weekendSchedulePastWinnersListView.setHasFixedSize(true)
            binding.weekendSchedulePastWinnersListView.adapter = pastWinnersAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
