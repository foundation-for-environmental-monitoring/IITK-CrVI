package io.ffem.iitk.util

import android.content.Context
import androidx.preference.PreferenceManager

fun getLong(context: Context?, key: String?): Long {
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    return sharedPreferences.getLong(key, -1L)
}

fun setLong(context: Context?, key: String?, value: Long) {
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    val editor = sharedPreferences.edit()
    editor.putLong(key, value)
    editor.apply()
}