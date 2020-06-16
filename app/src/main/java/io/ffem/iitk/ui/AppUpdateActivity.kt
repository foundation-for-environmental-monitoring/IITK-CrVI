package io.ffem.iitk.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.tasks.OnSuccessListener
import dagger.android.AndroidInjection
import java.util.concurrent.Executor
import javax.inject.Inject

private const val IMMEDIATE_UPDATE_REQUEST_CODE = 1000

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

        checkInAppUpdate()
    }

    private fun checkInAppUpdate() {
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener(playServiceExecutor, OnSuccessListener { appUpdateInfo ->
                when (appUpdateInfo.updateAvailability()) {
                    UpdateAvailability.UPDATE_AVAILABLE -> when {
                        appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE) -> startImmediateUpdate(
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

    private fun startImmediateUpdate(appUpdateInfo: AppUpdateInfo?) {
        appUpdateManager.startUpdateFlowForResult(
            appUpdateInfo,
            AppUpdateType.IMMEDIATE,
            this,
            IMMEDIATE_UPDATE_REQUEST_CODE
        )
    }
}