package com.opaku.id.core.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatImageView
import com.opaku.id.core.ui.binding.setImageResource
import java.io.IOException
import kotlin.math.roundToInt

fun Context.parsingFileToString(jsonFile: String): String? {
    return try {
        val `is` = assets.open(jsonFile)
        val buffer = ByteArray(`is`.available())
        `is`.read(buffer)
        `is`.close()
        String(buffer)

    } catch (ex: IOException) {
        ex.printStackTrace()
        null
    }
}

fun AppCompatImageView.setImageDrawableFromServer(resource: String) {
    val drawable = Drawable.createFromResourceStream(
        resources,
        TypedValue(),
        resources.assets.open("img/$resource"),
        null
    )

    this.setImageDrawable(drawable)
}

fun Context.toDp(number: Int): Int = (number * resources.displayMetrics.density).roundToInt()