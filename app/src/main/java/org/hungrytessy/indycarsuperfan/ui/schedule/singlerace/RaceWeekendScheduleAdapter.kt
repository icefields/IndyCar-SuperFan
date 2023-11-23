package org.hungrytessy.indycarsuperfan.ui.schedule.singlerace

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.hungrytessy.indycarsuperfan.R
import org.hungrytessy.indycarsuperfan.data.remote.dto.BaseStage
import org.hungrytessy.indycarsuperfan.data.remote.dto.indy.RaceWeekend
import java.util.TreeSet

class RaceWeekendScheduleAdapter(private val raceWeekend : RaceWeekend): RecyclerView.Adapter<StageScheduleHolder>() {
    private val stagesList: List<BaseStage> = ArrayList(TreeSet<BaseStage>().apply {
        addAll(raceWeekend.practice ?: TreeSet())
        raceWeekend.race?.let { add(it) }
        raceWeekend.qualification?.let { add(it) }
        // addAll(raceWeekend.qualification?.qualificationStages ?: TreeSet())
    }).reversed()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StageScheduleHolder {
        val viewLayout = LayoutInflater.from(parent.context).inflate(R.layout.item_race_schedule, parent,false)
        return StageScheduleHolder(viewLayout)
    }

    override fun onBindViewHolder(holder: StageScheduleHolder, position: Int) {
        holder.bind(stagesList[position])
    }

    override fun getItemCount() = stagesList.size
}

class StageScheduleHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val stageNameText : TextView = itemView.findViewById(R.id.stageNameText)
    private val stageDateText : TextView = itemView.findViewById(R.id.stageDateText)
    private val stageTimeText : TextView = itemView.findViewById(R.id.stageTimeText)

    fun bind(currentStage: BaseStage) {
        stageNameText.text = "${currentStage.description}"
        stageDateText.text = currentStage.getScheduledDateFormatted()
        stageTimeText.text = currentStage.getScheduledTimeFormatted()
    }
}
