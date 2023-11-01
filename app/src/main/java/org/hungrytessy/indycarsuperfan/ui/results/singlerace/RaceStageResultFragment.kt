package org.hungrytessy.indycarsuperfan.ui.results.singlerace

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.hungrytessy.indycarsuperfan.IndyFragment
import org.hungrytessy.indycarsuperfan.MainActivityNav
import org.hungrytessy.indycarsuperfan.R
import org.hungrytessy.indycarsuperfan.data.models.CompetitorEventSummary
import org.hungrytessy.indycarsuperfan.data.models.Driver
import org.hungrytessy.indycarsuperfan.data.models.indy.QualificationStage
import org.hungrytessy.indycarsuperfan.data.models.indy.Race
import org.hungrytessy.indycarsuperfan.data.models.indy.SingleRaceStage
import org.hungrytessy.indycarsuperfan.databinding.FragmentRaceStageResultsBinding
import org.hungrytessy.indycarsuperfan.ui.adapters.OnDriverClickListener
import java.util.TreeSet

private const val ARG_RACE_ID = "race_id"
private const val ARG_STAGE_ID = "stage_id"

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
    private lateinit var raceStageResultsViewModel: RaceStageResultViewModel
    private lateinit var adapter : StageResultAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        raceStageResultsViewModel = ViewModelProvider(this)[RaceStageResultViewModel::class.java]
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

            binding.driverNameTitle.text = "Driver"
            binding.driverLapsTitle.text = "Laps"

            when(it) {
                is Race -> {
                    binding.driverPointsTitle.text = "Pts"
                    binding.driverTimeTitle.text = "Time"
                    binding.driverAvgSpeedTitle.text = "AvgSpeed"
                }
                is QualificationStage -> {
                    binding.driverPointsTitle.visibility = View.GONE
                    binding.driverTimeTitle.text = "Time"
                    binding.driverAvgSpeedTitle.text = "Speed"
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
