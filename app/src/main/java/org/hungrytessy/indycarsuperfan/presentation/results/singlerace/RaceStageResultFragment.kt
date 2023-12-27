package org.hungrytessy.indycarsuperfan.presentation.results.singlerace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import org.hungrytessy.indycarsuperfan.R
import org.hungrytessy.indycarsuperfan.presentation.IndyFragment
import org.hungrytessy.indycarsuperfan.domain.model.QualificationStage
import org.hungrytessy.indycarsuperfan.domain.model.Race
import org.hungrytessy.indycarsuperfan.databinding.FragmentRaceStageResultsBinding
import org.hungrytessy.indycarsuperfan.presentation.adapters.OnDriverClickListener

private const val ARG_RACE_ID = "race_id"
private const val ARG_STAGE_ID = "stage_id"

@AndroidEntryPoint
class RaceStageResultFragment : IndyFragment(), OnDriverClickListener {
    companion object {
        @JvmStatic
        fun newInstance(raceId: String, stageId: String) = RaceStageResultFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_RACE_ID, raceId)
                putString(ARG_STAGE_ID, stageId)
            }
        }
    }

    private var _binding: FragmentRaceStageResultsBinding? = null
    private val binding get() = _binding!!
    private val raceStageResultsViewModel: RaceStageResultViewModel by viewModels()
    private lateinit var adapter : StageResultAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRaceStageResultsBinding.inflate(inflater, container, false)

        arguments?.getString(ARG_RACE_ID)?.let { raceId ->
            arguments?.getString(ARG_STAGE_ID)?.let { stageId ->
                raceStageResultsViewModel.initialize(raceId, stageId)
                initObservables()
            }
        }

        return binding.root
    }

    private fun initObservables() {
        raceStageResultsViewModel.stageResult.observe(viewLifecycleOwner) {
            adapter = StageResultAdapter(it, this)
            binding.stageResultsListView.layoutManager = LinearLayoutManager(requireContext())
            binding.stageResultsListView.setHasFixedSize(true)
            binding.stageResultsListView.adapter = adapter
            binding.stageResultsListView.isNestedScrollingEnabled = false

            binding.driverNameTitle.text = getString(R.string.race_results_table_title_driver)
            binding.driverLapsTitle.text = getString(R.string.race_results_table_title_laps)

            when(it) {
                is Race -> {
                    binding.driverPointsTitle.text = getString(R.string.race_results_table_title_pts)
                    binding.driverTimeTitle.text = getString(R.string.race_results_table_title_time)
                    binding.driverAvgSpeedTitle.text = getString(R.string.race_results_table_title_avgSpeed)
                }
                is QualificationStage -> {
                    binding.driverPointsTitle.visibility = View.GONE
                    binding.driverTimeTitle.text = getString(R.string.race_results_table_title_time)
                    binding.driverAvgSpeedTitle.text = getString(R.string.race_results_table_title_speed)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
