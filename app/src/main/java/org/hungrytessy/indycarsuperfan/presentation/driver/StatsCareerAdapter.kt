package org.hungrytessy.indycarsuperfan.presentation.driver

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.hungrytessy.indycarsuperfan.R
import org.hungrytessy.indycarsuperfan.data.remote.dto.Season
import org.hungrytessy.indycarsuperfan.domain.model.CompetitorEventSummary

class StatsCareerAdapter(
    private val statsList : Map<Season, CompetitorEventSummary>): RecyclerView.Adapter<StatsCareerAdapter.StatsCareerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsCareerHolder {
        val viewLayout = LayoutInflater.from(parent.context).inflate(
            org.hungrytessy.indycarsuperfan.R.layout.item_stats_career,
            parent,false)
        return StatsCareerHolder(viewLayout)
    }

    override fun onBindViewHolder(holder: StatsCareerHolder, position: Int) {
        val currentSeason = ArrayList(statsList.keys) [position]
        val currentSeasonResult = statsList [currentSeason]
        currentSeasonResult?.let { holder.bind(it, currentSeason) }

    }

    override fun getItemCount() = statsList.keys.size

    class StatsCareerHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val pointsText : TextView = itemView.findViewById(R.id.pointsCareerTxt)
        private val positionText : TextView = itemView.findViewById(R.id.positionCareerTxt)
        private val racesText : TextView = itemView.findViewById(R.id.racesCareerTxt)
        private val seasonText : TextView = itemView.findViewById(R.id.seasonCareerTxt)
        private val top10Text : TextView = itemView.findViewById(R.id.top10CareerTxt)
        private val top5Text : TextView = itemView.findViewById(R.id.top5CareerTxt)
        private val winsText : TextView = itemView.findViewById(R.id.winsCareerTxt)

        fun bind(seasonSummary: CompetitorEventSummary, season: Season) {
            racesText.text = "${seasonSummary.result?.races}"
            top10Text.text = "${seasonSummary.result?.top10}"
            top5Text.text = "${seasonSummary.result?.top5}"
            winsText.text = "${seasonSummary.result?.victories}"

            seasonText.text = "${season.description?.replace("Indycar", "")?.trim()}"
            positionText.text = "${seasonSummary.result?.position}"
            pointsText.text = "${seasonSummary.result?.points}"
        }
    }
}
