package io.ffem.iitk.update.di

import androidx.test.platform.app.InstrumentationRegistry
import io.ffem.iitk.app.App

object TestInjector {
    fun inject(): TestAppComponent {
        val application = InstrumentationRegistry.getInstrumentation()
            .targetContext.applicationContext as App
        return DaggerTestAppComponent.factory()
            .create(application)
            .also { it.inject(application) } as TestAppComponent
    }
}
