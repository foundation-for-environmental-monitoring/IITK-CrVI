package io.ffem.iitk

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import io.ffem.iitk.ui.main.MainFragment
import io.ffem.iitk.ui.main.ResultFragment
import io.ffem.iitk.ui.main.TreatmentTypeFragment
import org.json.JSONException

const val TEST_ID_KEY = "testId"
const val RESULT_JSON = "resultJson"
const val EXTERNAL_REQUEST = 1

class MainActivity : AppCompatActivity() {

    override fun setTitle(titleId: Int) {
        super.setTitle(titleId)

        findViewById<TextView>(R.id.appBarTitle).text = title
    }

    private var externalAppAction: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    private fun launchTest() {

        externalAppAction = "io.ffem.water"
        val testId = "d488672f-9a4c-4aa4-82eb-8a95c40d0296"

        val data = Bundle()
        try {
            data.putString(TEST_ID_KEY, testId)
            data.putBoolean("debugMode", true)
            val intent = Intent(externalAppAction)
            intent.putExtras(data)
            startActivityForResult(intent, EXTERNAL_REQUEST)

        } catch (e: ActivityNotFoundException) {
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
                    supportFragmentManager.popBackStackImmediate(
                        null,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, ResultFragment.newInstance(jsonString, ""))
                        .commitNow()

                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }

    fun inputWaterButtonClick(@Suppress("UNUSED_PARAMETER") view: View) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_left, R.anim.slide_out_left,
                R.anim.slide_in_right, R.anim.slide_out_right
            )
            .replace(R.id.container, TreatmentTypeFragment.newInstance("", ""))
            .addToBackStack(null)
            .commit()
    }

    fun outputWaterButtonClick(@Suppress("UNUSED_PARAMETER") view: View) {
        launchTest()
    }

    fun ironSulphateButtonClick(@Suppress("UNUSED_PARAMETER") view: View) {
        launchTest()
    }

    fun electrocoagulationButtonClick(@Suppress("UNUSED_PARAMETER") view: View) {
        launchTest()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}