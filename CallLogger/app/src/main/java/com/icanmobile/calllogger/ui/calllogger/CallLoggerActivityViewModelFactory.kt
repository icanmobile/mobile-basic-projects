package com.icanmobile.calllogger.ui.calllogger

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

/**
 * Created by JONG HO BAEK on 29,April,2019
 * email: icanmobile@gmail.com
 *
 * CallLoggerActivityViewModelFactory class
 */
class CallLoggerActivityViewModelFactory @Inject constructor(private val callLoggerActivityViewModel: CallLoggerActivityViewModel)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CallLoggerActivityViewModel::class.java)) {
            return callLoggerActivityViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}