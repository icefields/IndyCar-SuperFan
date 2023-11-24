package org.hungrytessy.indycarsuperfan.presentation.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prof18.rssparser.RssParserBuilder
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssItem
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import org.hungrytessy.indycarsuperfan.extensions.isoZonedDateToLocalDateTime
import org.hungrytessy.indycarsuperfan.extensions.rssDateStringToLocalDateTime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.TreeSet

const val RSS_YOUTUBE = "https://www.youtube.com/feeds/videos.xml?channel_id=UCy1F61QvUUQXAXi2Voa_fUw"
const val RSS_MOTORSPORT = "https://www.motorsport.com/rss/indycar/news/"
val RSS_DATE_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy hh:mm a")

class NewsViewModel : ViewModel() {
    private val _feed = MutableLiveData<List<RssItem>>()
    val feed: LiveData<List<RssItem>> = _feed

    fun fetchFeed() {
        viewModelScope.launch {
            val rssParser = RssParserBuilder(callFactory = OkHttpClient(), charset = Charsets.UTF_8,).build()
            val rssMotorsports: RssChannel = rssParser.getRssChannel(RSS_MOTORSPORT)
            val rssYoutube: RssChannel = rssParser.getRssChannel(RSS_YOUTUBE)

            val allRssTree: TreeSet<RssItem> = TreeSet { o1, o2 ->
                val date1 = try {
                    o1.pubDate!!.isoZonedDateToLocalDateTime()
                } catch (e: Exception) {
                    try {
                        rssDateStringToLocalDateTime(o1.pubDate!!)
                    } catch (e: Exception) {
                        LocalDateTime.now()
                    }
                }

                val date2 = try {
                    o2.pubDate!!.isoZonedDateToLocalDateTime()
                } catch (e: Exception) {
                    try {
                        rssDateStringToLocalDateTime(o2.pubDate!!)
                    } catch (e: Exception) {
                        LocalDateTime.now()
                    }
                }

                if (date1.isBefore(date2)) {
                    1;
                } else if (date1.isAfter(date2)) {
                    -1;
                } else {
                    0
                }

            }
            allRssTree.addAll(rssMotorsports.items)
            allRssTree.addAll(rssYoutube.items)
            _feed.value = ArrayList(allRssTree)
        }
    }
}
