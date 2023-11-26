package org.hungrytessy.indycarsuperfan.presentation.drivers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.hungrytessy.indycarsuperfan.presentation.IndyFragment
import org.hungrytessy.indycarsuperfan.databinding.FragmentDriverListBinding
import org.hungrytessy.indycarsuperfan.presentation.adapters.OnDriverClickListener

@AndroidEntryPoint
class DriverListFragment : IndyFragment(), OnDriverClickListener {
    // This property is only valid between onCreateView and onDestroyView.
    private var _binding: FragmentDriverListBinding? = null
    private val binding get() = _binding!!
    private val driversViewModel: DriverListViewModel by viewModels()
    private lateinit var adapter : DriverListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDriverListBinding.inflate(inflater, container, false)
        initObservables()
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
