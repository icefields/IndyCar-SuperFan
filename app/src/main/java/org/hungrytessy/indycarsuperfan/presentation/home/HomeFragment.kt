package org.hungrytessy.indycarsuperfan.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.hungrytessy.indycarsuperfan.presentation.IndyFragment
import org.hungrytessy.indycarsuperfan.presentation.MainActivityNav
import org.hungrytessy.indycarsuperfan.databinding.FragmentHomeBinding
import org.hungrytessy.indycarsuperfan.presentation.adapters.DriversAdapter
import org.hungrytessy.indycarsuperfan.presentation.adapters.OnDriverClickListener

@AndroidEntryPoint
class HomeFragment : IndyFragment(), OnDriverClickListener {
    // This property is only valid between onCreateView and onDestroyView.
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var adapter : DriversAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.viewAll.setOnClickListener {
            if (activity is MainActivityNav) {
                (activity as MainActivityNav).switchToResults()
            }
        }

        initObservables()
        homeViewModel.fetchNextRace()
        homeViewModel.fetchCurrentStandings()

        return binding.root
    }

    private fun initObservables() {
        homeViewModel.countdownString.observe(viewLifecycleOwner) { countdownStr ->
            _binding?.countdownText?.text = countdownStr
        }

        homeViewModel.currentStandings.observe(viewLifecycleOwner) {
            adapter = DriversAdapter(it.take(5), this)
            binding.homeListView.layoutManager = LinearLayoutManager(requireContext())
            binding.homeListView.setHasFixedSize(true)
            binding.homeListView.adapter = adapter
        }

        homeViewModel.nextRace.observe(viewLifecycleOwner) {
            val location = "${it.venue?.name}, ${it.venue?.city} (${it.venue?.countryCode})"
            val dateStr = it.getScheduledDateTimeFormatted()
            _binding?.raceLocationText?.text = location
            _binding?.raceNameText?.text = it.description
            _binding?.dateText?.text = dateStr
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        homeViewModel.cleanup()
        _binding = null
    }

//    private fun initViewModel() {
//        homeViewModel = ViewModelProvider(this, factory = object : ViewModelProvider.Factory {
//            @Suppress("UNCHECKED_CAST")
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                return HomeViewModel(IndyDataStore) as T
//            }
//        })[HomeViewModel::class.java]
//    }
}
