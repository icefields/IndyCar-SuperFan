package org.hungrytessy.indycarsuperfan.presentation.adapters

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.hungrytessy.indycarsuperfan.R
import org.hungrytessy.indycarsuperfan.common.getAssetUrlBigNumber
import org.hungrytessy.indycarsuperfan.common.getFlagDrawable
import org.hungrytessy.indycarsuperfan.domain.model.CompetitorEventSummary

open class DriversHolderSmall(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val driverImage : ImageView = itemView.findViewById(R.id.driverImg)
    private val driverNameText : TextView = itemView.findViewById(R.id.driverName)
    protected val positionText : TextView = itemView.findViewById(R.id.positionText)
    protected val pointsText : TextView = itemView.findViewById(R.id.driverPoints)
    private val countryImg : ImageView = itemView.findViewById(R.id.countryImg)

    open fun bind(currentDriver: CompetitorEventSummary, listener: OnDriverClickListener) {
        Glide.with(driverImage)
            .asBitmap()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .load(Uri.parse(currentDriver.getAssetUrlBigNumber()))
            .into(driverImage)

        currentDriver.driver?.getFlagDrawable()?.let { countryImg.setImageResource(it) }
        driverNameText.text = currentDriver.driver?.name ?: currentDriver.id.replace("sr:competitor:","")
        positionText.text = "${currentDriver.result?.position}"
        pointsText.text = "${currentDriver.result?.points}"

        itemView.setOnClickListener { listener.onDriverClick(currentDriver.driver) }
    }
}
