package org.hungrytessy.indycarsuperfan.ui.drivers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.hungrytessy.indycarsuperfan.IndyFragment
import org.hungrytessy.indycarsuperfan.databinding.FragmentDriverListBinding
import org.hungrytessy.indycarsuperfan.ui.adapters.OnDriverClickListener

class DriverListFragment : IndyFragment(), OnDriverClickListener {
    // This property is only valid between onCreateView and onDestroyView.
    private var _binding: FragmentDriverListBinding? = null
    private val binding get() = _binding!!
    private lateinit var driversViewModel: DriverListViewModel
    private lateinit var adapter : DriverListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        driversViewModel = ViewModelProvider(this)[DriverListViewModel::class.java]
        _binding = FragmentDriverListBinding.inflate(inflater, container, false)

        initObservables()
        driversViewModel.fetchDrivers()

        return binding.root
    }

    private fun initObservables() {
        driversViewModel.driverList.observe(viewLifecycleOwner) {
            adapter = DriverListAdapter(it, this)
            binding.driversListView.layoutManager = LinearLayoutManager(requireContext())
            binding.driversListView.setHasFixedSize(true)
            binding.driversListView.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
