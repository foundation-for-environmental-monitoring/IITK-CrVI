package io.ffem.iitk

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import io.ffem.iitk.helper.ApkHelper
import io.ffem.iitk.ui.*
import kotlinx.android.synthetic.main.app_bar_layout.*
import org.json.JSONException
import java.util.*

const val TEST_ID_KEY = "testId"
const val RESULT_JSON = "resultJson"
const val EXTERNAL_REQUEST = 1
const val TREATMENT_TYPE = "treatmentType"
const val PLAY_STORE_URL = "https://play.google.com/store/apps/details?id="

const val ARG_RESULT_JSON = "resultJson"

class MainActivity : AppUpdateActivity() {

    private lateinit var waterType: WaterType

    override fun setTitle(titleId: Int) {
        super.setTitle(titleId)
        findViewById<TextView>(R.id.appBarTitle).text = title
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (toolbar != null) {
            try {
                setSupportActionBar(toolbar)
            } catch (ignored: Exception) {
                // do nothing
            }
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        try {
            // If app has expired then close this activity
            ApkHelper.isAppVersionExpired(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        if (toolbar != null) {
            try {
                setSupportActionBar(toolbar)
            } catch (ignored: Exception) { // do nothing
            }
        }
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            supportActionBar!!.title = ""
        }
    }

    private fun launchTest(type: WaterType) {
        val appTitle = "ffem Water"
        val externalAppAction = "io.ffem.water"
        val testId = "d488672f-9a4c-4aa4-82eb-8a95c40d0296"
        waterType = type

        val data = Bundle()
        try {
            data.putString(TEST_ID_KEY, testId)
            data.putString(TREATMENT_TYPE, waterType.toString())
            val intent = Intent(externalAppAction)
            intent.putExtras(data)
            startActivityForResult(intent, EXTERNAL_REQUEST)
        } catch (e: ActivityNotFoundException) {
            val builder = AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog)
            builder.setTitle(R.string.app_not_found)
                .setMessage(String.format(Locale.US, getString(R.string.install_app), appTitle))
                .setPositiveButton(R.string.go_to_play_store) { _: DialogInterface?, _: Int ->
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(PLAY_STORE_URL + externalAppAction)
                        )
                    )
                }
                .setNegativeButton(
                    android.R.string.cancel
                ) { dialogInterface: DialogInterface, _: Int -> dialogInterface.dismiss() }
                .setCancelable(false)
                .show()
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == EXTERNAL_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                displayResult(intent)
            }
        }
    }

    private fun displayResult(intent: Intent?) {
        intent!!.extras
        if (intent.hasExtra(RESULT_JSON)) {
            val jsonString = intent.getStringExtra(RESULT_JSON)
            try {
                if (jsonString != null) {
                    supportFragmentManager.popBackStackImmediate()

                    if (waterType == WaterType.INPUT) {
                        supportFragmentManager.beginTransaction()
                            .replace(
                                R.id.container,
                                ResultTreatmentFragment.newInstance(jsonString)
                            )
                            .addToBackStack(null)
                            .commit()
                    } else {
                        supportFragmentManager.beginTransaction()
                            .replace(
                                R.id.container,
                                ResultFragment.newInstance(jsonString)
                            )
                            .addToBackStack(null)
                            .commit()
                    }

                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }

    fun inputWaterButtonClick(@Suppress("UNUSED_PARAMETER") view: View) {
        launchTest(WaterType.INPUT)
    }

    fun outputWaterButtonClick(@Suppress("UNUSED_PARAMETER") view: View) {
        launchTest(WaterType.OUTPUT)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun onInfoClick(@Suppress("UNUSED_PARAMETER") item: MenuItem) {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }
}