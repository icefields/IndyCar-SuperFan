package org.hungrytessy.indycarsuperfan.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import org.hungrytessy.indycarsuperfan.IndyFragment
import org.hungrytessy.indycarsuperfan.R
import org.hungrytessy.indycarsuperfan.databinding.FragmentScheduleBinding

class ScheduleFragment : IndyFragment() {
    private lateinit var scheduleViewModel: ScheduleViewModel
    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!
    private lateinit var resultsAdapter: SchedulePagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        scheduleViewModel = ViewModelProvider(this)[ScheduleViewModel::class.java]
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
