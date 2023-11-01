package org.hungrytessy.indycarsuperfan.ui.results.singlerace

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.hungrytessy.indycarsuperfan.data.models.indy.Qualification
import org.hungrytessy.indycarsuperfan.data.models.indy.QualificationStage
import org.hungrytessy.indycarsuperfan.data.models.indy.Race
import org.hungrytessy.indycarsuperfan.data.models.indy.RaceWeekend
import java.util.TreeSet

class RaceResultsPagerAdapter(fragment: Fragment, private val raceWeekend: RaceWeekend) : FragmentStateAdapter(fragment) {
    private var race: Race? = raceWeekend.race
    private var qualification: Qualification? = raceWeekend.qualification
    private var qualificationStages: List<QualificationStage> = ArrayList(qualification?.qualificationStages ?: TreeSet()).reversed()

    override fun getItemCount(): Int = (qualification?.qualificationStages?.size ?: 0) + 1

    override fun createFragment(position: Int): Fragment =
        when(position) {
            0 -> {
                RaceStageResultFragment.newInstance(
                    raceId = raceWeekend.id,
                    stageId = race?.id ?: ""
                )
            }
            else -> {
                RaceStageResultFragment.newInstance(
                    raceId = raceWeekend.id,
                    stageId = qualificationStages[position - 1].id
                )
            }
        }
}
