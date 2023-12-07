package org.hungrytessy.indycarsuperfan.presentation.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.hungrytessy.indycarsuperfan.presentation.IndyFragment
import org.hungrytessy.indycarsuperfan.databinding.FragmentStandingsBinding
import org.hungrytessy.indycarsuperfan.presentation.adapters.DriversAdaptersSmall
import org.hungrytessy.indycarsuperfan.presentation.adapters.OnDriverClickListener

@AndroidEntryPoint
class StandingsFragment : IndyFragment(), OnDriverClickListener {
    private lateinit var adapter : DriversAdaptersSmall
    private var _binding: FragmentStandingsBinding? = null
    private val binding get() = _binding!!
    private val standingsViewModel: StandingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStandingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        standingsViewModel.currentStandings.observe(viewLifecycleOwner) {
            adapter = DriversAdaptersSmall(it, this)
            binding.standingsListView.layoutManager = LinearLayoutManager(requireContext())
            binding.standingsListView.setHasFixedSize(true)
            binding.standingsListView.adapter = adapter
        }

        return root
    }
}
