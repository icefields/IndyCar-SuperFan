package org.hungrytessy.indycarsuperfan.presentation.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.hungrytessy.indycarsuperfan.presentation.IndyFragment
import org.hungrytessy.indycarsuperfan.databinding.FragmentFutureracesBinding
import org.hungrytessy.indycarsuperfan.presentation.adapters.OnRaceClickListener
import org.hungrytessy.indycarsuperfan.presentation.adapters.ScheduleAdapter

class FutureRacesFragment : IndyFragment(), OnRaceClickListener {
    private lateinit var resultsViewModel: FutureRacesViewModel
    private var _binding: FragmentFutureracesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter : ScheduleAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        resultsViewModel = ViewModelProvider(this)[FutureRacesViewModel::class.java]
        _binding = FragmentFutureracesBinding.inflate(inflater, container, false)
        initObservables()
        resultsViewModel.fetchFutureRaces()
        return binding.root
    }

    private fun initObservables() {
        resultsViewModel.futureRaces.observe(viewLifecycleOwner) { races ->
            adapter = ScheduleAdapter(races, this)
            binding.racesListView.layoutManager = LinearLayoutManager(requireContext())
            binding.racesListView.setHasFixedSize(true)
            binding.racesListView.adapter = adapter
        }
    }

    override fun onRaceClick(raceId: String) {
        goToWeekendRaceSchedule(raceId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}