package org.hungrytessy.indycarsuperfan.presentation.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.hungrytessy.indycarsuperfan.presentation.IndyFragment
import org.hungrytessy.indycarsuperfan.databinding.FragmentPastracesBinding
import org.hungrytessy.indycarsuperfan.presentation.adapters.OnDriverClickListener
import org.hungrytessy.indycarsuperfan.presentation.adapters.OnRaceClickListener

@AndroidEntryPoint
class PastRacesFragment : IndyFragment(), OnRaceClickListener, OnDriverClickListener {
    private val resultsViewModel: PastRacesViewModel by viewModels()
    private var _binding: FragmentPastracesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter : PastRacesExpandableListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPastracesBinding.inflate(inflater, container, false)
        initObservables()
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
