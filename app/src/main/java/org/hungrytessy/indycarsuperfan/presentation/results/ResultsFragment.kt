package org.hungrytessy.indycarsuperfan.presentation.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.hungrytessy.indycarsuperfan.presentation.IndyFragment
import org.hungrytessy.indycarsuperfan.databinding.FragmentResultsBinding
import org.hungrytessy.indycarsuperfan.presentation.adapters.OnRaceClickListener
import org.hungrytessy.indycarsuperfan.presentation.adapters.ScheduleAdapter

@AndroidEntryPoint
class ResultsFragment : IndyFragment(), OnRaceClickListener {
    private val resultsViewModel: ResultsViewModel by viewModels()
    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter : ScheduleAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentResultsBinding.inflate(inflater, container, false)
        initObservables()
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
