package io.ffem.iitk

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import io.ffem.iitk.ui.main.SectionsPagerAdapter

const val TEST_ID_KEY = "testId"
const val EXTERNAL_REQUEST = 1

class MainActivity : AppCompatActivity() {

    private var externalAppAction: String? = null

    fun setAppBarTitle(title: String) {
        findViewById<TextView>(R.id.appBarTitle).text = title
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager, 3)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    setAppBarTitle(getString(R.string.select_water_type))
                } else {
                    setAppBarTitle(getString(R.string.select_treatment_type))
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    fun inputWaterButtonClick(@Suppress("UNUSED_PARAMETER") view: View) {
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.currentItem = viewPager.currentItem + 1
    }

    fun outputWaterButtonClick(@Suppress("UNUSED_PARAMETER") view: View) {
        launchTest()
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

    fun ironSulphateButtonClick(@Suppress("UNUSED_PARAMETER") view: View) {
        launchTest()
    }

    fun electrocoagulationButtonClick(@Suppress("UNUSED_PARAMETER") view: View) {
        launchTest()
    }
}