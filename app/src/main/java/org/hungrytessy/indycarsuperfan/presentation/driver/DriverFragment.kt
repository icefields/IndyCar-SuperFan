package org.hungrytessy.indycarsuperfan.presentation.driver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.hungrytessy.indycarsuperfan.presentation.IndyFragment
import org.hungrytessy.indycarsuperfan.presentation.MainActivity
import org.hungrytessy.indycarsuperfan.databinding.FragmentDriverBinding
import org.hungrytessy.indycarsuperfan.common.getFlagDrawable
import org.hungrytessy.indycarsuperfan.common.loadDriverImage

private const val ARG_DRIVER_ID = "driver_id"

@AndroidEntryPoint
class DriverFragment : IndyFragment() {
    // This property is only valid between onCreateView and onDestroyView.
    private var _binding: FragmentDriverBinding? = null
    private val binding get() = _binding!!
    private val driverViewModel: DriverViewModel by viewModels()
    private lateinit var adapter : StatsCareerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        arguments?.getString(ARG_DRIVER_ID)?.let { driverId -> driverViewModel.initialize(driverId) }
        _binding = FragmentDriverBinding.inflate(inflater, container, false)

        initObservables()
        return binding.root
    }

    private fun initObservables() {
        driverViewModel.driver.observe(viewLifecycleOwner) { driver ->
            driver.competitor?.name?.let { name ->
                binding.driverNameTxt.text = name
                if (requireActivity() is MainActivity) {
                    (requireActivity() as MainActivity).supportActionBar?.title = name
                }
            }
            binding.driverBdayTxt.text = driver.getTeamsString()
            binding.teamNameTxt.text = driver.getTeamsString()
            binding.flagImg.setImageResource(driver.getFlagDrawable())
        }

        driverViewModel.driverResultsAll.observe(viewLifecycleOwner) {
            adapter = StatsCareerAdapter(it)
            binding.statsCareerLayout.careerStatsListView.layoutManager = LinearLayoutManager(requireContext())
            binding.statsCareerLayout.careerStatsListView.setHasFixedSize(true)
            binding.statsCareerLayout.careerStatsListView.adapter = adapter
        }

        driverViewModel.driverResultsCurrentSeasons.observe(viewLifecycleOwner) {
            it?.result?.let { result ->
                binding.driverImg.loadDriverImage(it.getDriver())
                binding.statsLayout.podiumsTxt.text = "${result.podiums?:""}"
                binding.statsLayout.currentStandingTxt.text = "${result.position?:""}"
                binding.statsLayout.winsTxt.text = "${result.victories?:""}"
                binding.statsLayout.pointsTxt.text = "${result.points?:""}"
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}