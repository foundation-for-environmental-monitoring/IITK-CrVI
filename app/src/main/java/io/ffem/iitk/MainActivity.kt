package io.ffem.iitk

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import io.ffem.iitk.ui.main.*
import io.ffem.iitk.ui.main.dummy.DummyContent
import org.json.JSONException

const val TEST_ID_KEY = "testId"
const val RESULT_JSON = "resultJson"
const val EXTERNAL_REQUEST = 1
const val TREATMENT_TYPE = "treatmentType"

class MainActivity : AppCompatActivity(), ItemFragment.OnListFragmentInteractionListener {

    private lateinit var treatmentType: TreatmentType

    override fun setTitle(titleId: Int) {
        super.setTitle(titleId)
        findViewById<TextView>(R.id.appBarTitle).text = title
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    private fun launchTest(type: TreatmentType) {
        val externalAppAction = "io.ffem.water"
        val testId = "d488672f-9a4c-4aa4-82eb-8a95c40d0296"
        treatmentType = type

        val data = Bundle()
        try {
            data.putString(TEST_ID_KEY, testId)
            data.putBoolean("debugMode", true)
            data.putString(TREATMENT_TYPE, treatmentType.toString())
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
                        .replace(
                            R.id.container,
                            ResultFragment.newInstance(jsonString, treatmentType)
                        )
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
            .replace(R.id.container, ItemFragment.newInstance(1))
            .addToBackStack(null)
            .commit()
    }

    fun outputWaterButtonClick(@Suppress("UNUSED_PARAMETER") view: View) {
        launchTest(TreatmentType.NONE)
    }

    fun ironSulphateButtonClick(@Suppress("UNUSED_PARAMETER") view: View) {
        showInstruction(TreatmentType.IRON_SULPHATE)
    }

    private fun showInstruction(treatmentType: TreatmentType) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_left, R.anim.slide_out_left,
                R.anim.slide_in_right, R.anim.slide_out_right
            )
            .replace(R.id.container, InstructionFragment.newInstance("", ""))
            .addToBackStack(null)
            .commit()
    }

    fun electrocoagulationButtonClick(@Suppress("UNUSED_PARAMETER") view: View) {
        showInstruction(TreatmentType.ELECTROCOAGULATION)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun startClick(@Suppress("UNUSED_PARAMETER") view: View) {
        launchTest(TreatmentType.IRON_SULPHATE)
    }

    override fun onListFragmentInteraction(item: DummyContent.DummyItem?) {
        Handler().postDelayed(
            {
                showInstruction(TreatmentType.ELECTROCOAGULATION)
            }, 350
        )
    }
}