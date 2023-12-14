package org.hungrytessy.indycarsuperfan.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.AssetManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.ImageView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.prof18.rssparser.model.RssItem
import org.hungrytessy.indycarsuperfan.R
import org.hungrytessy.indycarsuperfan.domain.model.BaseStage
import org.hungrytessy.indycarsuperfan.domain.model.Driver
import org.hungrytessy.indycarsuperfan.domain.model.IndyRssItem
import java.io.IOException
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


const val PLACEHOLDER = "__placeholder__"

val AppCompatActivity.navController: NavController? get() {
    val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment?
    return navHostFragment?.navController
}

fun replacePlaceholder(type: AssetImageType, name: String): String = type.type.replace(PLACEHOLDER, name)

// -------- Duration (Compatibility) --------

fun Duration.toDaysPartCompat(): Long = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
    toDaysPart()
} else {
    seconds / 86400
}

fun Duration.toHoursPartCompat(): Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
    toHoursPart()
} else {
    (toHours() % 24).toInt()
}

fun Duration.toMinutesPartCompat(): Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
    toMinutesPart()
} else {
    (toMinutes() % 60).toInt()
}

fun Duration.toSecondsPartCompat(): Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
    toSecondsPart()
} else {
    (seconds % 60).toInt()
}

// -------- END Duration (Compatibility) --------

/**
 * 2022-02-25T19:05:00+00:00
 * YYYY-MM-DDThh:mm:ssTZD (eg
 * 1997-07-16T19:20:30+01:00)
 * val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssO")//("YYYY-MM-DD'T'hh:mm:ss'T'ZD")
 */
fun String.isoZonedDateToLocalDateTime(): LocalDateTime =
    LocalDateTime.parse(this, DateTimeFormatter.ISO_ZONED_DATE_TIME)

fun Int.addZeroToSingleDigit(): String = if (this<10) "0$this" else this.toString()

fun rssDateStringToLocalDateTime(date: String): LocalDateTime =
    LocalDateTime.parse(date, DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss '+0000'"))

// -------- Context --------

fun Context.readStringAsset(fileName : String) : String {
    return assets.open(fileName).bufferedReader().use { it.readText()}
}

fun Context.isDarkModeOn() = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES

@Throws(IOException::class)
fun Context.getBitmapFromAssets(fileName: String, type: AssetImageType): Bitmap? {
    val assetManager: AssetManager = assets
    val istr = assetManager.open(replacePlaceholder(type, fileName))
    val bitmap = BitmapFactory.decodeStream(istr)
    istr.close()
    return bitmap
}


@ColorInt
@SuppressLint("Recycle")
fun Context.themeColor(@AttrRes themeAttrId: Int): Int {
    return obtainStyledAttributes(
        intArrayOf(themeAttrId)
    ).use {
        it.getColor(0, Color.MAGENTA)
    }
}

// -------- END Context --------

fun Driver.getFlagDrawable(): Int = when((nationality ?: "").lowercase()) {
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


