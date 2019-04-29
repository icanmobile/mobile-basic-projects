package com.icanmobile.calllogger.di

import androidx.lifecycle.ViewModelProvider
import com.icanmobile.calllogger.ui.calllogger.CallLoggerActivityViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by JONG HO BAEK on 29,April,2019
 * email: icanmobile@gmail.com
 *
 * ViewModelModule class
 */
@Module
class ViewModelModule {

    @Provides
    @Singleton
    fun provideCallLoggerActivityViewModel(factory: CallLoggerActivityViewModelFactory): ViewModelProvider.Factory = factory

}