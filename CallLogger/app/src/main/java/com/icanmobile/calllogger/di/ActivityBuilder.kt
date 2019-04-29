package com.icanmobile.calllogger.di

import com.icanmobile.calllogger.ui.calllogger.CallLoggerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by JONG HO BAEK on 29,April,2019
 * email: icanmobile@gmail.com
 *
 * ActivityBuilder class
 */
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun bindCallLoggerActivity(): CallLoggerActivity
}