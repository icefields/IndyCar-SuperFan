package org.hungrytessy.indycarsuperfan.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.hungrytessy.indycarsuperfan.R
import org.hungrytessy.indycarsuperfan.domain.model.CompetitorEventSummary

class DriversAdaptersSmall(
    private val driverList : List<CompetitorEventSummary>,
    private val clickListener: OnDriverClickListener
): RecyclerView.Adapter<DriversHolderSmall>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriversHolderSmall {
        val viewLayout = LayoutInflater.from(parent.context).inflate(
            R.layout.item_standings_small,
            parent,false)
        return DriversHolderSmall(viewLayout)
    }

    override fun onBindViewHolder(holder: DriversHolderSmall, position: Int) = holder.bind(driverList[position], clickListener)
    override fun getItemCount() = driverList.size
}
