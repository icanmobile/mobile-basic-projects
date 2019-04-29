package com.icanmobile.calllogger.di

import android.app.Application
import com.icanmobile.calllogger.CallLoggerApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Created by JONG HO BAEK on 29,April,2019
 * email: icanmobile@gmail.com
 *
 * ApplicationComponent interface
 */
@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class, ApplicationModule::class, ActivityBuilder::class))
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder
        fun build(): ApplicationComponent
    }

    fun inject(app: CallLoggerApp)
}