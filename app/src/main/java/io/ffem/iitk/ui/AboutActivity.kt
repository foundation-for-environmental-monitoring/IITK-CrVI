package io.ffem.iitk.ui

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.ffem.iitk.R
import kotlinx.android.synthetic.main.activity_about.*

/**
 * Activity to display info about the app.
 */
class AboutActivity : AppCompatActivity() {

    private var dialog: NoticesDialogFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        textVersion.text = getAppVersion()

        setTitle(R.string.about)

        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
    }

    /**
     * Gets the app version.
     *
     * @return The version name and number
     */
    private fun getAppVersion(): String {
        var version = ""
        try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)
            version = String.format("%s %s", getString(R.string.version), packageInfo.versionName)
        } catch (ignored: PackageManager.NameNotFoundException) {
            // do nothing
        }

        return version
    }

    /**
     * Displays legal information.
     */
    fun onSoftwareNoticesClick(@Suppress("UNUSED_PARAMETER") view: View) {
        dialog = NoticesDialogFragment.newInstance()
        dialog!!.show(supportFragmentManager, "NoticesDialog")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
