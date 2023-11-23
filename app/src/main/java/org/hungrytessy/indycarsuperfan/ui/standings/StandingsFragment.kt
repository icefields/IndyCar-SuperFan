package org.hungrytessy.indycarsuperfan.ui.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import org.hungrytessy.indycarsuperfan.IndyFragment
import org.hungrytessy.indycarsuperfan.data.IndyDataStore
import org.hungrytessy.indycarsuperfan.databinding.FragmentStandingsBinding
import org.hungrytessy.indycarsuperfan.ui.adapters.DriversAdaptersSmall
import org.hungrytessy.indycarsuperfan.ui.adapters.OnDriverClickListener

/**
 */
class StandingsFragment : IndyFragment(), OnDriverClickListener {
    private lateinit var adapter : DriversAdaptersSmall
    private var _binding: FragmentStandingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStandingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapter = DriversAdaptersSmall(IndyDataStore.getCurrentStanding(), this)
        binding.standingsListView.layoutManager = LinearLayoutManager(requireContext())
        binding.standingsListView.setHasFixedSize(true)
        binding.standingsListView.adapter = adapter

        return root
    }
}
