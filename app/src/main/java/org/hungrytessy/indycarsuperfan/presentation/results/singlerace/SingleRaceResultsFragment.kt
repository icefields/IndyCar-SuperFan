package org.hungrytessy.indycarsuperfan.presentation.results.singlerace

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import org.hungrytessy.indycarsuperfan.presentation.IndyFragment
import org.hungrytessy.indycarsuperfan.presentation.MainActivity
import org.hungrytessy.indycarsuperfan.data.remote.dto.Venue
import org.hungrytessy.indycarsuperfan.databinding.FragmentSingleRaceResultsBinding
import org.hungrytessy.indycarsuperfan.extensions.getTrackDrawable
import java.util.TreeSet

private const val ARG_RACE_ID = "race_id"

class SingleRaceResultsFragment : IndyFragment() {
    private lateinit var viewModel: SingleRaceResultsViewModel
    private var _binding: FragmentSingleRaceResultsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter : RaceResultsPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel = ViewModelProvider(this)[SingleRaceResultsViewModel::class.java]
        arguments?.getString(ARG_RACE_ID)?.let { raceId -> viewModel.initialize(raceId) }
        _binding = FragmentSingleRaceResultsBinding.inflate(inflater, container, false)
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
            adapter = RaceResultsPagerAdapter(this, raceWeekend)
            binding.venueView.raceDate.text = raceWeekend.race?.getScheduledDateTimeFormatted()
            binding.venueView.trackImg.setImageResource(raceWeekend.getTrackDrawable())
            binding.raceResultsViewpager.adapter = adapter

            TabLayoutMediator(binding.raceResultsTabLayout, binding.raceResultsViewpager) { tab, position ->
                tab.text = when(position) {
                    0 -> raceWeekend.race?.description ?: "ERROR"
                    else -> ArrayList(raceWeekend.qualification?.qualificationStages ?: TreeSet()).reversed()[position-1].getStageName(true)
                }
            }.attach()
        }

        viewModel.venue.observe(viewLifecycleOwner) { venue ->
            setBarText(venue)
            binding.venueView.raceMiles.text = "${venue.length} m"
            binding.venueView.raceLocation.text = venue.city
            binding.venueView.raceName.text = venue.name
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
