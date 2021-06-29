package com.opaku.id.core.utils

import android.content.Context
import java.io.IOException

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