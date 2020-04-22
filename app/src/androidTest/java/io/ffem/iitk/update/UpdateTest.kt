package io.ffem.iitk.update

import android.os.SystemClock
import androidx.appcompat.widget.AppCompatButton
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.testing.FakeAppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType
import io.ffem.iitk.BuildConfig
import io.ffem.iitk.MainActivity
import io.ffem.iitk.update.di.TestInjector
import io.ffem.iitk.util.TestHelper
import io.ffem.iitk.util.mDevice
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

// https://github.com/malvinstn/FakeAppUpdateManagerSample
@RunWith(AndroidJUnit4::class)
class UpdateTest {

    private lateinit var fakeAppUpdateManager: FakeAppUpdateManager

    companion object {
        @JvmStatic
        @BeforeClass
        fun initialize() {
            if (!TestHelper.isDeviceInitialized()) {
                mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
            }
        }
    }

    @Before
    fun setUp() {
        val component = TestInjector.inject()
        fakeAppUpdateManager = component.fakeAppUpdateManager()
    }

    @Test
    fun app_Update_Completes() {
        fakeAppUpdateManager.partiallyAllowedUpdateType = AppUpdateType.FLEXIBLE
        fakeAppUpdateManager.setUpdateAvailable(BuildConfig.VERSION_CODE + 1)

        ActivityScenario.launch(MainActivity::class.java)

        SystemClock.sleep(3000)

        assertTrue(fakeAppUpdateManager.isConfirmationDialogVisible)

        fakeAppUpdateManager.userAcceptsUpdate()

        fakeAppUpdateManager.downloadStarts()

        fakeAppUpdateManager.downloadCompletes()

        SystemClock.sleep(3000)

        onView(
            allOf(
                isDescendantOfA(instanceOf(Snackbar.SnackbarLayout::class.java)),
                instanceOf(AppCompatButton::class.java)
            )
        ).perform(ViewActions.click())

        assertTrue(fakeAppUpdateManager.isInstallSplashScreenVisible)

        fakeAppUpdateManager.installCompletes()
    }

    @Test
    fun app_Update_DownloadFails() {
        fakeAppUpdateManager.partiallyAllowedUpdateType = AppUpdateType.FLEXIBLE
        fakeAppUpdateManager.setUpdateAvailable(BuildConfig.VERSION_CODE + 1)

        ActivityScenario.launch(MainActivity::class.java)

        SystemClock.sleep(3000)

        assertTrue(fakeAppUpdateManager.isConfirmationDialogVisible)

        fakeAppUpdateManager.userAcceptsUpdate()

        fakeAppUpdateManager.downloadStarts()

        fakeAppUpdateManager.downloadFails()

        SystemClock.sleep(3000)

        onView(
            allOf(
                isDescendantOfA(instanceOf(Snackbar.SnackbarLayout::class.java)),
                instanceOf(AppCompatButton::class.java)
            )
        ).perform(ViewActions.click())

        assertFalse(fakeAppUpdateManager.isInstallSplashScreenVisible)

        assertTrue(fakeAppUpdateManager.isConfirmationDialogVisible)
    }
}