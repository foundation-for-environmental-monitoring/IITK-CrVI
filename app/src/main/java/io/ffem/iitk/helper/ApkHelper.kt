package io.ffem.iitk.helper

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import io.ffem.iitk.BuildConfig
import io.ffem.iitk.R
import java.util.*

object ApkHelper {
    /**
     * Checks if app version has expired and if so displays an expiry message and closes activity.
     *
     * @param activity The activity
     * @return True if the app has expired
     */
    fun isAppVersionExpired(activity: Activity): Boolean {
        if (BuildConfig.BUILD_TYPE.equals("release", ignoreCase = true) &&
            isNonStoreVersion(activity)
        ) {
            val marketUrl = Uri.parse(
                "https://play.google.com/store/apps/details?id=" +
                        activity.packageName
            )
            val appExpiryDate = GregorianCalendar.getInstance()
            appExpiryDate.time = BuildConfig.BUILD_TIME
            appExpiryDate.add(Calendar.DAY_OF_YEAR, 15)
            if (GregorianCalendar().after(appExpiryDate)) {
                val message = String.format(
                    "%s%n%n%s", activity.getString(R.string.thisVersionHasExpired),
                    activity.getString(R.string.uninstallAndInstallFromStore)
                )
                val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
                builder.setTitle(R.string.versionExpired)
                    .setMessage(message)
                    .setCancelable(false)
                builder.setPositiveButton(
                    R.string.ok
                ) { dialogInterface: DialogInterface, _: Int ->
                    dialogInterface.dismiss()
                    try {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = marketUrl
                        intent.setPackage("com.android.vending")
                        activity.startActivity(intent)
                    } catch (ignore: Exception) {
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        activity.finishAndRemoveTask()
                    } else {
                        activity.finish()
                    }
                }
                val alertDialog = builder.create()
                alertDialog.show()
                return true
            }
        }
        return false
    }

    /**
     * Checks if the app was installed from the app store or from an install file.
     * source: http://stackoverflow.com/questions/37539949/detect-if-an-app-is-installed-from-play-store
     *
     * @param context The context
     * @return True if app was not installed from the store
     */
    private fun isNonStoreVersion(context: Context): Boolean {

        // Valid installer package names
        val validInstallers: List<String> =
            ArrayList(
                listOf(
                    "com.android.vending",
                    "com.google.android.feedback"
                )
            )
        try {
            // The package name of the app that has installed the app
            val installer =
                context.packageManager.getInstallerPackageName(context.packageName)

            // true if the app has been downloaded from Play Store
            return installer == null || !validInstallers.contains(installer)
        } catch (ignored: Exception) {
            // do nothing
        }
        return true
    }
}