package io.ffem.iitk.app

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.ffem.iitk.di.DaggerAppComponent

class App : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        app = this
    }

    companion object {
        /**
         * Gets the singleton app object.
         *
         * @return the singleton app
         */
        lateinit var app: App
            private set // Singleton
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }
}