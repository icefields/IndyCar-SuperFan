package org.hungrytessy.indycarsuperfan.ui.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.hungrytessy.indycarsuperfan.IndyFragment
import org.hungrytessy.indycarsuperfan.databinding.FragmentResultsBinding
import org.hungrytessy.indycarsuperfan.ui.adapters.OnRaceClickListener
import org.hungrytessy.indycarsuperfan.ui.adapters.ScheduleAdapter

class ResultsFragment : IndyFragment(), OnRaceClickListener {
    private lateinit var resultsViewModel: ResultsViewModel
    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter : ScheduleAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        resultsViewModel = ViewModelProvider(this)[ResultsViewModel::class.java]
        _binding = FragmentResultsBinding.inflate(inflater, container, false)
        initObservables()
        resultsViewModel.fetchCurrentSeasonResults()
        return binding.root
    }

    private fun initObservables() {
        resultsViewModel.pastRaces.observe(viewLifecycleOwner) { races ->
            adapter = ScheduleAdapter(races, this)
            binding.racesListView.layoutManager = LinearLayoutManager(requireContext())
            binding.racesListView.setHasFixedSize(true)
            binding.racesListView.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRaceClick(raceId: String) {
        goToWeekendRaceResults(raceId)
    }
}
