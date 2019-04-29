package com.icanmobile.calllogger

import android.app.Activity
import android.app.Application
import com.icanmobile.calllogger.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by JONG HO BAEK on 29,April,2019
 * email: icanmobile@gmail.com
 *
 * CallLoggerApp class
 */
class CallLoggerApp: Application(), HasActivityInjector {
    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        DaggerApplicationComponent.builder()
            .application(this)
            .build()
            .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector
}