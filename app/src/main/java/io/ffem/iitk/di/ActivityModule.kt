package io.ffem.iitk.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.ffem.iitk.MainActivity

@Suppress("unused")
@Module
interface ActivityModule {
    @ContributesAndroidInjector
    fun contributesMainActivity(): MainActivity
}
