package org.hungrytessy.indycarsuperfan.ui.drivers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.hungrytessy.indycarsuperfan.R
import org.hungrytessy.indycarsuperfan.data.IndyDataStore
import org.hungrytessy.indycarsuperfan.data.models.Driver
import org.hungrytessy.indycarsuperfan.extensions.getFlagDrawable
import org.hungrytessy.indycarsuperfan.extensions.loadDriverImage
import org.hungrytessy.indycarsuperfan.ui.adapters.OnDriverClickListener

class DriverListAdapter(
    private val driverList : List<Driver>,
    private val clickListener: OnDriverClickListener
): RecyclerView.Adapter<DriverListAdapter.DriverListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverListHolder {
        val viewLayout = LayoutInflater.from(parent.context).inflate(
            org.hungrytessy.indycarsuperfan.R.layout.item_driver,
            parent,false)
        return DriverListHolder(viewLayout)
    }

    override fun onBindViewHolder(holder: DriverListHolder, position: Int) {
        val currentDriver = driverList[position]
        holder.bind(currentDriver, clickListener)
    }

    override fun getItemCount() = driverList.size

    class DriverListHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val driverImage : ImageView = itemView.findViewById(R.id.driverImg)
        private val countryImg : ImageView = itemView.findViewById(R.id.countryImg)
        private val driverNameText : TextView = itemView.findViewById(R.id.driverName)
        private val teamText : TextView = itemView.findViewById(R.id.driverTeam)

        fun bind(currentDriver: Driver, listener: OnDriverClickListener) {
            driverNameText.text = currentDriver.competitor?.name

            countryImg.setImageResource(currentDriver.getFlagDrawable())
            itemView.setOnClickListener { listener.onDriverClick(currentDriver) }

            if (currentDriver.getTeamsString().isNotEmpty()) {
                teamText.visibility = View.VISIBLE
                teamText.text = "${currentDriver.getTeamsString()}"
            } else {
                teamText.visibility = View.GONE
            }

            driverImage.loadDriverImage(currentDriver)
        }
    }
}
