package org.hungrytessy.indycarsuperfan.ui.schedule.singlerace

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.hungrytessy.indycarsuperfan.IndyFragment
import org.hungrytessy.indycarsuperfan.MainActivity
import org.hungrytessy.indycarsuperfan.data.models.Driver
import org.hungrytessy.indycarsuperfan.data.models.Venue
import org.hungrytessy.indycarsuperfan.databinding.FragmentSingleRaceScheduleBinding
import org.hungrytessy.indycarsuperfan.extensions.getTrackDrawable
import org.hungrytessy.indycarsuperfan.ui.adapters.OnDriverClickListener

private const val ARG_RACE_ID = "race_id"

class SingleRaceScheduleFragment : IndyFragment(), OnDriverClickListener {
    private lateinit var viewModel: SingleRaceScheduleViewModel
    private var _binding: FragmentSingleRaceScheduleBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RaceWeekendScheduleAdapter
    private lateinit var pastWinnersAdapter: PastWinnersAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel = ViewModelProvider(this)[SingleRaceScheduleViewModel::class.java]
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
            binding.venueView.trackImg.setImageResource(raceWeekend.getTrackDrawable())
            //binding.trackBigImg.setImageResource(raceWeekend.getTrackDrawable())
        }

        viewModel.venue.observe(viewLifecycleOwner) { venue ->
            setBarText(venue)
            binding.venueView.raceMiles.text = "${venue.length} m"
            binding.venueView.raceLocation.text = venue.city
            binding.venueView.raceName.text = venue.name
        }

        viewModel.pastWinners.observe(viewLifecycleOwner) { pastWinners ->
            for (key in pastWinners.keys) {
                Log.d("LUCIFER", "$key ${pastWinners[key]?.competitor?.name}")
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