package org.hungrytessy.indycarsuperfan.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.AssetManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.hungrytessy.indycarsuperfan.R
import org.hungrytessy.indycarsuperfan.data.models.BaseStage
import org.hungrytessy.indycarsuperfan.data.models.Driver
import java.io.IOException
import java.security.AccessController.getContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


private const val PLACEHOLDER = "__placeholder__"

val AppCompatActivity.navController: NavController? get() {
    val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment?
    return navHostFragment?.navController
}

fun Context.readStringAsset(fileName : String) : String {
    return assets.open(fileName).bufferedReader().use { it.readText()}
}

fun Context.isDarkModeOn() = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES

fun ImageView.loadDriverImage(driver: Driver?) {
    Glide.with(this)
        .asBitmap()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(R.drawable.img_placeholder)
        .error(R.drawable.img_placeholder)
        .load(Uri.parse(driver?.getAssetUrlHeadshot()))
        // .override(2600, 2200)
        .centerCrop()
        .into(this)
}

fun String.isoZonedDateToLocalDateTime(): LocalDateTime {
    // 2022-02-25T19:05:00+00:00
    // YYYY-MM-DDThh:mm:ssTZD (eg
    // 1997-07-16T19:20:30+01:00)
    //val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssO")//("YYYY-MM-DD'T'hh:mm:ss'T'ZD")
    return LocalDateTime.parse(this, DateTimeFormatter.ISO_ZONED_DATE_TIME)
}

fun rssDateStringToLocalDateTime(date: String): LocalDateTime =
    LocalDateTime.parse(date, DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss '+0000'"))

@Throws(IOException::class)
fun Context.getBitmapFromAssets(fileName: String, type: AssetImageType): Bitmap? {
    val assetManager: AssetManager = assets
    val istr = assetManager.open(replacePlaceholder(type, fileName))
    val bitmap = BitmapFactory.decodeStream(istr)
    istr.close()
    return bitmap
}

@Throws(IOException::class)
fun Context.getBitmapFromAssetsSmallNumber(fileName: String): Bitmap? =
    getBitmapFromAssets(fileName, AssetImageType.NUMBER_S)

@ColorInt
@SuppressLint("Recycle")
fun Context.themeColor(@AttrRes themeAttrId: Int): Int {
    return obtainStyledAttributes(
        intArrayOf(themeAttrId)
    ).use {
        it.getColor(0, Color.MAGENTA)
    }
}

@Throws(IOException::class)
fun Context.getBitmapFromAssetsBigNumber(fileName: String): Bitmap {
    var bmp: Bitmap? = null
    try {
        bmp = getBitmapFromAssets(fileName.toString(), AssetImageType.NUMBER_M)
    } catch (e: Exception) { }

    if (bmp == null) {
        return (ResourcesCompat.getDrawable(resources, R.drawable.ic_logo_text_img, null) as BitmapDrawable?)!!.bitmap
    }
    return bmp
}

fun Int.addZeroToSingleDigit(): String = if (this<10) "0$this" else this.toString()

/**
 * use with glide
 */
fun getAssetUrlBigNumber(carNumber: Int, driver: Driver?): String {
    val carNumberStr: String = if (carNumber == 6
        && driver?.competitor?.name?.contains("Helio") == true) {
        "0${carNumber}"
    } else {
        "$carNumber"
    }
    return "file:///android_asset/"+ replacePlaceholder(AssetImageType.NUMBER_M, carNumberStr)
}

fun Driver.getAssetUrlHeadshot(): String {
    val name = (competitor?.name ?: "").split(", ")[1].lowercase()
    val lastName = (competitor?.name ?: "").split(", ")[0].lowercase().replace(" ", "_")
    return "file:///android_asset/images/headshots/img_${name[0]}_${lastName}.png"
}

fun replacePlaceholder(type: AssetImageType, name: String): String {
    return type.type.replace(PLACEHOLDER, name)
}

enum class AssetImageType(val type: String) {
    NUMBER_S("images/numbers/${PLACEHOLDER}.png"),
    NUMBER_M("images/numbers/${PLACEHOLDER}M.png"),
    HEADSHOT("images/headshots/Img_${PLACEHOLDER}.png")
}

fun BaseStage.getTrackDrawable(): Int {
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

fun Driver.getFlagDrawable(): Int = when((competitor?.nationality ?: "").lowercase()) {
    "argentina" -> R.drawable.flag_argentina
    "australia" -> R.drawable.flag_australia
    "brazil" -> R.drawable.flag_brazil
    "canada" -> R.drawable.flag_canada
    "denmark" -> R.drawable.flag_denmark
    "france" -> R.drawable.flag_france
    "mexico" -> R.drawable.flag_mexico
    "netherlands" -> R.drawable.flag_netherlands
    "new zealand" -> R.drawable.flag_newzealand
    "spain" -> R.drawable.flag_spain
    "sweden" -> R.drawable.flag_sweden
    "united kingdom" -> R.drawable.flag_uk
    "great britain" -> R.drawable.flag_uk
    "usa" -> R.drawable.flag_unitedstates
    "japan" -> R.drawable.flag_japan
    "estonia" -> R.drawable.flag_estonia
    "italy" -> R.drawable.flag_italy
    "colombia" -> R.drawable.flag_colombia
    "russia" -> R.drawable.flag_russia
    "austria" -> R.drawable.flag_austria
    "switzerland" -> R.drawable.flag_switzerland
    else -> R.drawable.ic_menu_drivers
}
