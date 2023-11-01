package org.hungrytessy.indycarsuperfan.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import org.hungrytessy.indycarsuperfan.IndyFragment
import org.hungrytessy.indycarsuperfan.data.models.Stage
import org.hungrytessy.indycarsuperfan.databinding.FragmentPastracesBinding
import org.hungrytessy.indycarsuperfan.ui.adapters.OnDriverClickListener
import org.hungrytessy.indycarsuperfan.ui.adapters.OnRaceClickListener


class PastRacesFragment : IndyFragment(), OnRaceClickListener, OnDriverClickListener {
    private lateinit var resultsViewModel: PastRacesViewModel
    private var _binding: FragmentPastracesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter : PastRacesExpandableListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        resultsViewModel = ViewModelProvider(this)[PastRacesViewModel::class.java]
        _binding = FragmentPastracesBinding.inflate(inflater, container, false)
        initObservables()
        resultsViewModel.fetchPastRaces()
        return binding.root
    }

    private fun initObservables() {
        resultsViewModel.pastRaces.observe(viewLifecycleOwner) { races ->
            adapter = PastRacesExpandableListAdapter(races, this, this)
            binding.racesListView.setAdapter(adapter)
            //binding.racesListView.setOnGroupExpandListener(OnGroupExpandListener { groupPosition -> })
            //binding.racesListView.setOnGroupCollapseListener(OnGroupCollapseListener { groupPosition -> })
            //binding.racesListView.setOnChildClickListener(OnChildClickListener { parent, v, groupPosition, childPosition, id -> false })
        }
    }

    override fun onRaceClick(raceId: String) {
        goToWeekendRaceResults(raceId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
