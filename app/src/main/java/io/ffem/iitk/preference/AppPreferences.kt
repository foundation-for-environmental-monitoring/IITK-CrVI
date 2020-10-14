package io.ffem.iitk.preference

//
//fun saveLastAppUpdateCheck() {
//    setLong(App.app, "lastUpdateCheck", Calendar.getInstance().timeInMillis)
//}
//
//fun isAppUpdateCheckRequired(): Boolean {
//    if (BuildConfig.DEBUG) {
//        return true
//    }
//    val lastCheck: Long = getLong(App.app, "lastUpdateCheck")
//    return TimeUnit.MILLISECONDS.toDays(
//        Calendar.getInstance().timeInMillis - lastCheck
//    ) > 0
//}