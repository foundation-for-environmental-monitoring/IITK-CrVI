package io.ffem.iitk.update.di

import com.google.android.play.core.appupdate.testing.FakeAppUpdateManager
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import io.ffem.iitk.app.App
import io.ffem.iitk.di.ActivityModule
import io.ffem.iitk.di.AppComponent
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityModule::class,
        TestAppModule::class
    ]
)
interface TestAppComponent : AppComponent {

    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<App>

    fun fakeAppUpdateManager(): FakeAppUpdateManager
}
