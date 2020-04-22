package io.ffem.iitk.ui

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.tasks.OnSuccessListener
import dagger.android.AndroidInjection
import io.ffem.iitk.R
import io.ffem.iitk.preference.isAppUpdateCheckRequired
import io.ffem.iitk.preference.saveLastAppUpdateCheck
import java.util.concurrent.Executor
import javax.inject.Inject

private const val FLEXIBLE_UPDATE_REQUEST_CODE = 1200

/**
 * The base activity for activities where app update has to be checked
 * based on sample: https://github.com/malvinstn/FakeAppUpdateManagerSample
 */
abstract class AppUpdateActivity : AppCompatActivity() {

    @Inject
    lateinit var appUpdateManager: AppUpdateManager

    @Inject
    lateinit var playServiceExecutor: Executor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        appUpdateManager.registerListener(listener)

        if (isAppUpdateCheckRequired()) {
            checkInAppUpdate()
        }
    }

    override fun onResume() {
        super.onResume()

        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener(playServiceExecutor, OnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                    popupSnackbarForCompleteUpdate()
                }
            })
    }


    private val listener = { state: InstallState ->
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            popupSnackbarForCompleteUpdate()
        } else if (state.installStatus() == InstallStatus.FAILED) {
            popupSnackbarForRetryUpdate()
        }
    }

    private fun checkInAppUpdate() {
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener(playServiceExecutor, OnSuccessListener { appUpdateInfo ->
                when (appUpdateInfo.updateAvailability()) {
                    UpdateAvailability.UPDATE_AVAILABLE -> when {
                        appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE) -> startFlexibleUpdate(
                            appUpdateInfo
                        )
                        else -> {
                            // No update is allowed
                        }
                    }
                    else -> {
                        // No op
                    }
                }
            })
    }

    private fun startFlexibleUpdate(appUpdateInfo: AppUpdateInfo?) {
        appUpdateManager.startUpdateFlowForResult(
            appUpdateInfo,
            AppUpdateType.FLEXIBLE,
            this,
            FLEXIBLE_UPDATE_REQUEST_CODE
        )
        saveLastAppUpdateCheck()
    }

    private fun popupSnackbarForCompleteUpdate() {
        Snackbar.make(
            findViewById(R.id.mainLayout),
            R.string.update_ready,
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction(R.string.restart) { appUpdateManager.completeUpdate() }
            val textView: TextView = view.findViewById(R.id.snackbar_text)
            textView.setTextColor(Color.WHITE)
            show()
        }
    }

    private fun popupSnackbarForRetryUpdate() {
        Snackbar.make(
            findViewById(R.id.mainLayout),
            R.string.update_download_fail,
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction(R.string.retry) { checkInAppUpdate() }
            val textView: TextView = view.findViewById(R.id.snackbar_text)
            textView.setTextColor(Color.WHITE)
            show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        appUpdateManager.unregisterListener(listener)
    }
}