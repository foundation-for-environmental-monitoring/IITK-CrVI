package io.ffem.iitk


import android.os.SystemClock
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import io.ffem.iitk.util.TestHelper
import io.ffem.iitk.util.TestHelper.childAtPosition
import io.ffem.iitk.util.mDevice
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.IsInstanceOf
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class InputWaterTest {

    @get:Rule
    val mActivityTestRule = activityScenarioRule<MainActivity>()

    companion object {
        @JvmStatic
        @BeforeClass
        fun setup() {
            if (!TestHelper.isDeviceInitialized()) {
                mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
            }
        }
    }

    @Test
    fun ironSulphate_InputTest() {
        val appCompatButton = onView(
            allOf(
                withText("Input Water"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.container),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())

        SystemClock.sleep(2000)

        mDevice.findObject(By.text("Next")).click()

        SystemClock.sleep(4000)

        val textView2 = onView(
            allOf(
                withId(R.id.text_name), withText("Chromium (VI)"),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Chromium (VI)")))

        val textView6 = onView(
            allOf(
                withId(R.id.appBarTitle), withText("Result"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView6.check(matches(withText("Result")))
    }

    @Test
    fun electrocoagulation_InputTest() {
        val appCompatButton = onView(
            allOf(
                withText("Input Water"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.container),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())

        SystemClock.sleep(2000)

        mDevice.findObject(By.text("Next")).click()

        SystemClock.sleep(4000)

        val textView = onView(
            allOf(
                withId(R.id.appBarTitle), withText("Result"),
                childAtPosition(
                    allOf(
                        withId(R.id.toolbar),
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Result")))

        val textView2 = onView(
            allOf(
                withId(R.id.text_name), withText("Chromium (VI)"),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Chromium (VI)")))
    }
}
