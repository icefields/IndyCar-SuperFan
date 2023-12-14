package org.hungrytessy.indycarsuperfan.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.hungrytessy.indycarsuperfan.R
import org.hungrytessy.indycarsuperfan.domain.model.RaceWeekend

class ScheduleAdapter(
    private val racesList : List<RaceWeekend>,
    private val clickListener: OnRaceClickListener
): RecyclerView.Adapter<ScheduleAdapter.ScheduleRaceHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleRaceHolder {
        val viewLayout = LayoutInflater.from(parent.context).inflate(
            R.layout.item_schedule,
            parent,false)
        return ScheduleRaceHolder(viewLayout)
    }

    override fun onBindViewHolder(holder: ScheduleRaceHolder, position: Int) {
        val currentDriver = racesList[position]
        holder.bind(currentDriver, clickListener)
    }

    override fun getItemCount() = racesList.size

    class ScheduleRaceHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val stageNameText : TextView = itemView.findViewById(R.id.raceNameTxt)
        private val dateText : TextView = itemView.findViewById(R.id.raceDateTxt)
        private val raceLocationText : TextView = itemView.findViewById(R.id.raceLocationTxt)

        fun bind(currentRace: RaceWeekend, listener: OnRaceClickListener) {
            stageNameText.text = currentRace.description
            dateText.text = currentRace.getScheduledDateFormatted()
            raceLocationText.text = currentRace.venue?.city ?: ""
            itemView.setOnClickListener { listener.onRaceClick(currentRace.id) }
        }
    }
}
