package org.hungrytessy.indycarsuperfan.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.hungrytessy.indycarsuperfan.IndyFragment
import org.hungrytessy.indycarsuperfan.MainActivityNav
import org.hungrytessy.indycarsuperfan.data.IndyDataStore
import org.hungrytessy.indycarsuperfan.data.models.indy.RaceWeekend
import org.hungrytessy.indycarsuperfan.databinding.FragmentHomeBinding
import org.hungrytessy.indycarsuperfan.ui.adapters.DriversAdapter
import org.hungrytessy.indycarsuperfan.ui.adapters.OnDriverClickListener

class HomeFragment : IndyFragment(), OnDriverClickListener {
    // This property is only valid between onCreateView and onDestroyView.
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var adapter : DriversAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this, factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(IndyDataStore) as T
            }
        })[HomeViewModel::class.java]

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

    private fun test() {
        //Log.d("LUCIFER", "${ArrayList(RaceWeekend.seasonRacesFactory(IndyDataStore.getPreviousSeason()))[4].race?.description}")
        //Log.d("LUCIFER", "${ArrayList(RaceWeekend.seasonRacesFactory(IndyDataStore.getPreviousSeason()))[4].qualification?.qualificationStages?.first()?.result?.first()?.result?.carNumber}")
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
}