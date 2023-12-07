package org.hungrytessy.indycarsuperfan.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.hungrytessy.indycarsuperfan.common.loadDriverImage
import org.hungrytessy.indycarsuperfan.domain.model.CompetitorEventSummary

class DriversAdapter(
    private val driverList : List<CompetitorEventSummary>,
    private val clickListener: OnDriverClickListener
): RecyclerView.Adapter<DriversAdapter.DriversHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriversHolder {
        val viewLayout = LayoutInflater.from(parent.context).inflate(
            org.hungrytessy.indycarsuperfan.R.layout.item_small_list,
            parent,false)
        return DriversHolder(viewLayout)
    }

    override fun onBindViewHolder(holder: DriversHolder, position: Int) {
        val currentDriver = driverList[position]
        holder.bind(currentDriver, clickListener)
    }

    override fun getItemCount() = driverList.size

    class DriversHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val driverImage : ImageView = itemView.findViewById(org.hungrytessy.indycarsuperfan.R.id.driverImg)
        private val driverNameText : TextView = itemView.findViewById(org.hungrytessy.indycarsuperfan.R.id.driverName)
        private val positionText : TextView = itemView.findViewById(org.hungrytessy.indycarsuperfan.R.id.positionText)
        private val pointsText : TextView = itemView.findViewById(org.hungrytessy.indycarsuperfan.R.id.driverPoints)

        fun bind(currentDriver: CompetitorEventSummary, listener: OnDriverClickListener) {
            driverImage.loadDriverImage(currentDriver.driver)
            driverNameText.text = currentDriver.driver?.name
            positionText.text = "${currentDriver.result?.position}"
            pointsText.text = "${currentDriver.result?.points}pts"
            itemView.setOnClickListener { listener.onDriverClick(currentDriver.driver) }
        }
    }
}
