package org.hungrytessy.indycarsuperfan.ui.schedule.singlerace

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.hungrytessy.indycarsuperfan.R
import org.hungrytessy.indycarsuperfan.data.models.Driver
import org.hungrytessy.indycarsuperfan.ui.adapters.OnDriverClickListener

class PastWinnersAdapter(private val drivers : Map<String, Driver>, private val onDriverClickListener: OnDriverClickListener): RecyclerView.Adapter<PastWinnerHolder>() {
    private val yearsList: List<String> = ArrayList(drivers.keys).reversed()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PastWinnerHolder {
        val viewLayout = LayoutInflater.from(parent.context).inflate(R.layout.item_past_winners, parent,false)
        return PastWinnerHolder(viewLayout, onDriverClickListener)
    }

    override fun onBindViewHolder(holder: PastWinnerHolder, position: Int) {
        val key = yearsList[position] //?: ""
        holder.bind(key, drivers[key]!!)
    }

    override fun getItemCount() = drivers.size
}

class PastWinnerHolder(itemView: View, private val onDriverClickListener: OnDriverClickListener): RecyclerView.ViewHolder(itemView) {
    private val winnerName : TextView = itemView.findViewById(R.id.winnerName)
    private val winnerYear : TextView = itemView.findViewById(R.id.winnerYear)

    fun bind(year: String, driver: Driver) {
        winnerYear.text = year
        winnerName.text = "${driver.competitor?.name}"
        itemView.setOnClickListener { onDriverClickListener.onDriverClick(driver) }
    }
}
