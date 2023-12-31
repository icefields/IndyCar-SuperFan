package org.hungrytessy.indycarsuperfan.presentation.news

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.hungrytessy.indycarsuperfan.R
import org.hungrytessy.indycarsuperfan.common.RSS_DATE_FORMAT
import org.hungrytessy.indycarsuperfan.common.isoZonedDateToLocalDateTime
import org.hungrytessy.indycarsuperfan.common.rssDateStringToLocalDateTime
import org.hungrytessy.indycarsuperfan.domain.model.IndyRssItem

class RssFeedAdapter (private val items: List<IndyRssItem>) : RecyclerView.Adapter<RssFeedAdapter.RssViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RssViewHolder =
        RssViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_rss, parent, false))
    override fun onBindViewHolder(viewHolder: RssViewHolder, position: Int) =
        viewHolder.bind(items[position])
    override fun getItemCount(): Int =
        items.size

    inner class RssViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTxt = itemView.findViewById<TextView>(R.id.titleTxt)
        private val subtitleTxt = itemView.findViewById<TextView>(R.id.subtitleTxt)
        private val rssImage = itemView.findViewById<ImageView>(R.id.rssImage)
        private val dateTxt = itemView.findViewById<TextView>(R.id.dateTxt)

        fun bind(rssItem: IndyRssItem) {
            titleTxt.text = rssItem.title
            rssItem.description?.let {
                subtitleTxt.visibility = View.VISIBLE
                subtitleTxt.text = it
                rssItem.pubDate?.let { dateStr ->
                    dateTxt.text = rssDateStringToLocalDateTime(dateStr).format(RSS_DATE_FORMAT)
                }
            } ?: run {
                subtitleTxt.visibility = View.GONE
                dateTxt.text = rssItem.pubDate?.isoZonedDateToLocalDateTime()?.format(
                    RSS_DATE_FORMAT
                )
            }
            val imageUrl = rssItem.image ?: "https://img.youtube.com/vi/${rssItem.guid?.replace("yt:video:", "")}/hqdefault.jpg"
            Glide.with(rssImage).load(imageUrl).into(rssImage)

            itemView.setOnClickListener {
                itemView.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(rssItem.link)))
            }
        }
    }
}
