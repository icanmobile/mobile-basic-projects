package com.icanmobile.calllogger.di

import com.icanmobile.calllogger.data.DataManager
import com.icanmobile.calllogger.data.calllog.CallLogManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by JONG HO BAEK on 29,April,2019
 * email: icanmobile@gmail.com
 *
 * ApplicationModule class
 */
@Module(includes = [ViewModelModule::class])
class ApplicationModule {

    @Provides
    @Singleton
    fun provideDataManager(callLogManager: CallLogManager) = DataManager(callLogManager)

    @Provides
    @Singleton
    fun provideCallLogManager() = CallLogManager()
}