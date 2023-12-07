package org.hungrytessy.indycarsuperfan.presentation.results.singlerace

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.hungrytessy.indycarsuperfan.R
import org.hungrytessy.indycarsuperfan.data.remote.dto.CompetitorEventSummaryDto
import org.hungrytessy.indycarsuperfan.domain.model.CompetitorEventSummary
import org.hungrytessy.indycarsuperfan.domain.model.Race
import org.hungrytessy.indycarsuperfan.domain.model.SingleRaceStage
import org.hungrytessy.indycarsuperfan.presentation.adapters.OnDriverClickListener
import java.util.TreeSet

class StageResultAdapter(
    private val singleRaceStage : SingleRaceStage,
    private val clickListener: OnDriverClickListener
): RecyclerView.Adapter<StageResultHolder>() {
    private val driverList: List<CompetitorEventSummary> = ArrayList(singleRaceStage.result ?: TreeSet())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StageResultHolder {
        val viewLayout = LayoutInflater.from(parent.context).inflate(R.layout.item_race_result, parent,false)
        return when (singleRaceStage) {
            is Race -> StageResultHolderRace(viewLayout)
            else -> StageResultHolderQualify(viewLayout)
        }
    }

    override fun onBindViewHolder(holder: StageResultHolder, position: Int) {
        holder.bind(driverList[position], clickListener, position)
    }

    override fun getItemCount() = driverList.size
}

abstract class StageResultHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    protected val driverAvgSpeed : TextView = itemView.findViewById(R.id.driverAvgSpeed)
    protected val driverNameText : TextView = itemView.findViewById(R.id.driverName)
    protected val positionText : TextView = itemView.findViewById(R.id.positionText)
    protected val pointsText : TextView = itemView.findViewById(R.id.driverPoints)
    protected val driverTime : TextView = itemView.findViewById(R.id.driverTime)
    protected val driverLaps : TextView = itemView.findViewById(R.id.driverLaps)

    open fun bind(currentDriver: CompetitorEventSummary, listener: OnDriverClickListener, position: Int) {
        // alternate colours for readability
        itemView.setBackgroundColor(
            if (position % 2 == 0) {
                itemView.resources.getColor(R.color.black_900)
            } else {
                itemView.resources.getColor(R.color.black_800)
            }
        )
    }
}

class StageResultHolderQualify(itemView: View): StageResultHolder(itemView) {
    override fun bind(currentDriver: CompetitorEventSummary, listener: OnDriverClickListener, position: Int) {
        super.bind(currentDriver, listener, position)
        driverNameText.text = currentDriver.driver?.competitor?.name
        positionText.text = "${currentDriver.result?.position}"
        pointsText.visibility = View.GONE
        driverAvgSpeed.text = "${currentDriver.result?.bestSpeed}"
        val timeStr = currentDriver.result?.fastestLapTime ?: ""
        driverTime.text = (if(timeStr.length > 10) timeStr.substring(0, 10) else timeStr)
        driverLaps.text = "${currentDriver.result?.laps}"

        itemView.setOnClickListener { listener.onDriverClick(currentDriver.driver) }
    }
}

class StageResultHolderRace(itemView: View): StageResultHolder(itemView) {
    override fun bind(currentDriver: CompetitorEventSummary, listener: OnDriverClickListener, position: Int) {
        super.bind(currentDriver, listener, position)
        driverNameText.text = currentDriver.driver?.competitor?.name ?: currentDriver.id.replace("sr:competitor:","")
        positionText.text = "${currentDriver.result?.position}"
        driverAvgSpeed.text = "${currentDriver.result?.avgSpeed}"
        pointsText.text = "${currentDriver.result?.points}"
        driverLaps.text = "${currentDriver.result?.laps}"
        val timeStr = currentDriver.result?.time ?: ""
        driverTime.text = (if(timeStr.length > 10) timeStr.substring(0, 10) else timeStr).uppercase()
        itemView.setOnClickListener { listener.onDriverClick(currentDriver.driver) }
    }
}
