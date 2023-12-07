package org.hungrytessy.indycarsuperfan.presentation.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import org.hungrytessy.indycarsuperfan.presentation.IndyFragment
import org.hungrytessy.indycarsuperfan.databinding.FragmentScheduleBinding

@AndroidEntryPoint
class ScheduleFragment : IndyFragment() {
    private val scheduleViewModel: ScheduleViewModel by viewModels()
    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!
    private lateinit var resultsAdapter: SchedulePagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initAdapter()
    }

    private fun initAdapter() {
        resultsAdapter = SchedulePagerAdapter(this)
        binding.resultsViewpager.adapter = resultsAdapter

        TabLayoutMediator(binding.resultsTabLayout, binding.resultsViewpager) { tab, position ->
            tab.text = when(position) {
                0 -> "Upcoming Races"
                else -> "Past Races"
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class SchedulePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        when(position) {
            0 -> FutureRacesFragment()
            else -> PastRacesFragment()
        }
}
