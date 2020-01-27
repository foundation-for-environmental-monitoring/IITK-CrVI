package io.ffem.iitk


import android.os.SystemClock
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
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

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

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

        val linearLayout = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.list),
                        childAtPosition(
                            withId(R.id.container),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        linearLayout.perform(click())

        SystemClock.sleep(1000)

        mDevice.findObject(By.text("Next")).click()

        SystemClock.sleep(4000)

        val textView = onView(
            allOf(
                withText("Treatment"),
                childAtPosition(
                    allOf(
                        withId(R.id.infoLayout),
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.widget.RelativeLayout::class.java),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Treatment")))

        val textView2 = onView(
            allOf(
                withId(R.id.textTitle), withText("Chromium"),
                childAtPosition(
                    allOf(
                        withId(R.id.resultLayout),
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.widget.RelativeLayout::class.java),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Chromium")))

        val textView3 = onView(
            allOf(
                withId(R.id.textSubtitle), withText("Iron Sulphate"),
                childAtPosition(
                    allOf(
                        withId(R.id.resultLayout),
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.widget.RelativeLayout::class.java),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Iron Sulphate")))

        val textView4 = onView(
            allOf(
                withId(R.id.recommendation2Text),
                withText("Note: Based on each tablet containing 200mg of FeSO4.7H2O salt."),
                childAtPosition(
                    allOf(
                        withId(R.id.infoLayout),
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.widget.RelativeLayout::class.java),
                            1
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("Note: Based on each tablet containing 200mg of FeSO4.7H2O salt.")))

        val textView5 = onView(
            allOf(
                withId(R.id.recommendation1Text),
                withText(R.string.iron_sulphate_recommendation),
                childAtPosition(
                    allOf(
                        withId(R.id.infoLayout),
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.widget.RelativeLayout::class.java),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView5.check(matches(withText(R.string.iron_sulphate_recommendation)))

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

        val linearLayout = onView(
            allOf(
                childAtPosition(
                    allOf(
                        withId(R.id.list),
                        childAtPosition(
                            withId(R.id.container),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        linearLayout.perform(click())

        SystemClock.sleep(1000)

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
                withId(R.id.textTitle), withText("Chromium"),
                childAtPosition(
                    allOf(
                        withId(R.id.resultLayout),
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.widget.RelativeLayout::class.java),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("Chromium")))

        val textView3 = onView(
            allOf(
                withId(R.id.textSubtitle), withText("Electrocoagulation"),
                childAtPosition(
                    allOf(
                        withId(R.id.resultLayout),
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.widget.RelativeLayout::class.java),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("Electrocoagulation")))

        val textView4 = onView(
            allOf(
                withText("Treatment"),
                childAtPosition(
                    allOf(
                        withId(R.id.infoLayout),
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.widget.RelativeLayout::class.java),
                            1
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("Treatment")))

        val textView5 = onView(
            allOf(
                withId(R.id.recommendation1Text),
                withText(R.string.electrocoagulation_recommendation),
                childAtPosition(
                    allOf(
                        withId(R.id.infoLayout),
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.widget.RelativeLayout::class.java),
                            1
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView5.check(matches(withText(R.string.electrocoagulation_recommendation)))

        val textView6 = onView(
            allOf(
                withId(R.id.recommendation2Text),
                childAtPosition(
                    allOf(
                        withId(R.id.infoLayout),
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.widget.RelativeLayout::class.java),
                            1
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        textView6.check(doesNotExist())
    }
}
