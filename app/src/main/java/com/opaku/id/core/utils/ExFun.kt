package com.opaku.id.core.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
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

fun Context.parsingFileToStringFromInternalStorage(jsonFile: String): String? {
    return try {
        val mFile = File(filesDir, jsonFile)
        val `is` = FileInputStream(mFile)
        val buffer = ByteArray(`is`.available())
        `is`.read(buffer)
        `is`.close()
        String(buffer)

    } catch (ex: IOException) {
        ex.printStackTrace()
        null
    }
}

fun Context.writeFileToInternalStorage(data: String, jsonFile: String) {
    val mFile = File(filesDir, jsonFile)
    val stream = FileOutputStream(mFile)
    stream.use {
        it.write(data.toByteArray())
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

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}