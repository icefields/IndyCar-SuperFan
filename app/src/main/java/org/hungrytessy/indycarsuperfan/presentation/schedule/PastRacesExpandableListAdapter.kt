package org.hungrytessy.indycarsuperfan.presentation.schedule

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import org.hungrytessy.indycarsuperfan.R
import org.hungrytessy.indycarsuperfan.data.remote.dto.CompetitorEventSummaryDto
import org.hungrytessy.indycarsuperfan.domain.model.RaceWeekend
import org.hungrytessy.indycarsuperfan.common.themeColor
import org.hungrytessy.indycarsuperfan.domain.model.CompetitorEventSummary
import org.hungrytessy.indycarsuperfan.presentation.adapters.DriversHolderSmall
import org.hungrytessy.indycarsuperfan.presentation.adapters.OnDriverClickListener
import org.hungrytessy.indycarsuperfan.presentation.adapters.OnRaceClickListener

private const val CHILDREN_COUNT = 3
class PastRacesExpandableListAdapter(
    private val racesList : List<RaceWeekend>,
    private val raceClickListener: OnRaceClickListener,
    private val driverClickListener: OnDriverClickListener
) : BaseExpandableListAdapter() {
    override fun getChild(position: Int, expandedListPosition: Int): CompetitorEventSummary =  ArrayList(racesList[position].race?.result)[expandedListPosition]
    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long = if (expandedListPosition < CHILDREN_COUNT) {
        getChild(listPosition, expandedListPosition).hashCode().toLong()
    } else {
        984367592666036612L
    }
    override fun getChildrenCount(listPosition: Int): Int = CHILDREN_COUNT + 1 // (+1 because of footer)
    override fun getGroup(listPosition: Int): RaceWeekend = racesList[listPosition]
    override fun getGroupCount(): Int = racesList.size
    override fun getGroupId(listPosition: Int): Long = racesList[listPosition].hashCode().toLong()
    override fun hasStableIds(): Boolean = false
    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean = true

    override fun getChildView(listPosition: Int, expandedListPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        if (!isLastChild) {
            val competitorEventSummary = getChild(listPosition, expandedListPosition)
            val viewLayout = LayoutInflater.from(parent.context).inflate(R.layout.item_past_race_child, parent,false)
            val vh = PastWinnersSmallHolder(viewLayout)
            vh.bind(competitorEventSummary, driverClickListener)
            return viewLayout
        } else {
            val viewLayout = LayoutInflater.from(parent.context).inflate(R.layout.item_past_race_child_footer, parent,false)
            viewLayout.findViewById<View>(R.id.view_all).setOnClickListener { raceClickListener.onRaceClick(getGroup(listPosition).id) }
            return viewLayout
        }
    }

    override fun getGroupView(listPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
        val stage = getGroup(listPosition)
        val viewLayout = convertView ?: LayoutInflater.from(parent.context).inflate(R.layout.item_past_race, parent,false)
        val vh = PastRacesHolder(viewLayout, isExpanded)
        vh.bind(stage, raceClickListener)
        return viewLayout
    }
}

class PastRacesHolder(private val itemView: View, private val isExpanded: Boolean) {
    private val stageNameText : TextView = itemView.findViewById(R.id.raceNameTxt)
    private val dateText : TextView = itemView.findViewById(R.id.raceDateTxt)
    private val raceLocationText : TextView = itemView.findViewById(R.id.raceLocationTxt)
    private val arrowExpandImg : ImageView = itemView.findViewById(R.id.pastRaceExpandArrow)
    private val cardContainer : CardView = itemView.findViewById(R.id.cardContainer)

    fun bind(currentRace: RaceWeekend, listener: OnRaceClickListener) {
        cardContainer.radius =
            if (isExpanded) {
                0.0f
            } else {
                itemView.resources.getDimension(R.dimen.list_item_container_card_radius)
            }

        cardContainer.setCardBackgroundColor(
            if (isExpanded) {
                Color.TRANSPARENT
            } else {
                itemView.context.themeColor(com.google.android.material.R.attr.colorSurface)
            }
        )

        arrowExpandImg.setImageResource(
            if (isExpanded) {
                R.drawable.ic_drop_down_indicator_up
            } else {
                R.drawable.ic_drop_down_indicator
            })

        stageNameText.text = currentRace.description
        dateText.text = currentRace.getScheduledDateFormatted()
        raceLocationText.text = currentRace.venue?.city ?: ""
    }
}

class PastWinnersSmallHolder(private val itemView: View): DriversHolderSmall(itemView) {
    private val standingsSmallContainer : View = itemView.findViewById(R.id.standingsSmallContainer)

    override fun bind(currentDriver: CompetitorEventSummary, listener: OnDriverClickListener) {
        super.bind(currentDriver, listener)
        standingsSmallContainer.setBackgroundColor(itemView.resources.getColor(R.color.black_800))

        val timeStr = currentDriver.result?.time ?: ""
        pointsText.text = (if(timeStr.length > 10) timeStr.substring(0, 10) else timeStr)

        positionText.text = when(currentDriver.result?.position) {
            1 -> "1st"
            2 -> "2nd"
            3 -> "3rd"
            else -> "ERR"
        }
    }
}
