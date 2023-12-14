package org.hungrytessy.indycarsuperfan.common

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.prof18.rssparser.model.RssItem
import org.hungrytessy.indycarsuperfan.R
import org.hungrytessy.indycarsuperfan.domain.model.BaseStage
import org.hungrytessy.indycarsuperfan.domain.model.CompetitorEventSummary
import org.hungrytessy.indycarsuperfan.domain.model.Driver
import org.hungrytessy.indycarsuperfan.domain.model.IndyRssItem
import org.hungrytessy.indycarsuperfan.domain.model.RaceWeekend

fun ImageView.loadDriverImage(driver: Driver?) {
    Glide.with(this)
        .asBitmap()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(R.drawable.img_placeholder)
        .error(R.drawable.img_placeholder)
        .load(driver?.headShotUri)
        // .override(2600, 2200)
        .centerCrop()
        .into(this)
}

/**
 * use with glide
 */
fun CompetitorEventSummary.getAssetUrlBigNumber(): String {
    val carNumber: Int = result?.carNumber ?: -1
    val driver: Driver? = driver
    val carNumberStr: String = if (carNumber == 6
        && driver?.name?.contains("Helio") == true) {
        "0${carNumber}"
    } else {
        "$carNumber"
    }
    return "file:///android_asset/"+ replacePlaceholder(AssetImageType.NUMBER_M, carNumberStr)
}

enum class AssetImageType(val type: String) {
    NUMBER_S("images/numbers/$PLACEHOLDER.png"),
    NUMBER_M("images/numbers/${PLACEHOLDER}M.png"),
    HEADSHOT("images/headshots/Img_$PLACEHOLDER.png")
}

fun RaceWeekend.trackDrawableFromName(): Int {
    if (description?.lowercase()?.contains("One Million Challenge".lowercase()) == true) return R.drawable.img_placeholder
    if (description?.lowercase()?.contains("Milwaukee".lowercase()) == true) return R.drawable.img_placeholder

    if (description?.lowercase()?.contains("Grand Prix of Indianapolis".lowercase()) == true) return R.drawable.track_indianapolis_road
    if (description?.lowercase()?.contains("Madison".lowercase()) == true) return R.drawable.track_illinois
    if (description?.lowercase()?.contains("Portland".lowercase()) == true) return R.drawable.track_portland
    if (description?.lowercase()?.contains("Monterey".lowercase()) == true) return R.drawable.track_monterey
    if (description?.lowercase()?.contains("Petersburg".lowercase()) == true) return R.drawable.track_petersburg
    if (description?.lowercase()?.contains("Long Beach".lowercase()) == true) return R.drawable.track_long_beach
    if (description?.lowercase()?.contains("Alabama".lowercase()) == true) return R.drawable.track_alabama
    if (description?.lowercase()?.contains("Toronto".lowercase()) == true) return R.drawable.track_toronto
    if (description?.lowercase()?.contains("Iowa".lowercase()) == true) return R.drawable.track_iowa
    if (description?.lowercase()?.contains("Nashville".lowercase()) == true) return R.drawable.track_nashville
    if (description?.lowercase()?.contains("Indianapolis 500".lowercase()) == true) return R.drawable.track_indianapolis_500
    if (description?.lowercase()?.contains("Detroit".lowercase()) == true) return R.drawable.track_detroit
    if (description?.lowercase()?.contains("Road America".lowercase()) == true) return R.drawable.track_road_america
    if (description?.lowercase()?.contains("Lexington".lowercase()) == true) return R.drawable.track_mid_ohio

    return R.drawable.img_placeholder
}

fun RssItem.toIndyRssItem() = IndyRssItem(
    guid = guid,
    title = title,
    author = author,
    link = link,
    pubDate = pubDate,
    description = description,
    content = content,
    image = image,
    video = video,
    sourceName = sourceName,
    sourceUrl = sourceUrl,
    categories = categories
)


//
//@Throws(IOException::class)
//fun Context.getBitmapFromAssetsSmallNumber(fileName: String): Bitmap? =
//    getBitmapFromAssets(fileName, AssetImageType.NUMBER_S)
//
//@Throws(IOException::class)
//fun Context.getBitmapFromAssetsBigNumber(fileName: String): Bitmap {
//    var bmp: Bitmap? = null
//    try {
//        bmp = getBitmapFromAssets(fileName.toString(), AssetImageType.NUMBER_M)
//    } catch (e: Exception) { }
//
//    if (bmp == null) {
//        return (ResourcesCompat.getDrawable(resources, R.drawable.ic_logo_text_img, null) as BitmapDrawable?)!!.bitmap
//    }
//    return bmp
//}