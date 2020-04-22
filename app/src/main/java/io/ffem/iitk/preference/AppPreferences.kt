package io.ffem.iitk.preference

import io.ffem.iitk.BuildConfig
import io.ffem.iitk.app.App
import io.ffem.iitk.util.getLong
import io.ffem.iitk.util.setLong
import java.util.*
import java.util.concurrent.TimeUnit

fun saveLastAppUpdateCheck() {
    setLong(App.app, "lastUpdateCheck", Calendar.getInstance().timeInMillis)
}

fun isAppUpdateCheckRequired(): Boolean {
    if (BuildConfig.DEBUG) {
        return true
    }
    val lastCheck: Long = getLong(App.app, "lastUpdateCheck")
    return TimeUnit.MILLISECONDS.toDays(
        Calendar.getInstance().timeInMillis - lastCheck
    ) > 0
}