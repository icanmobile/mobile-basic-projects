package com.icanmobile.calllogger.ui.calllogger

import android.content.ContentResolver
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.icanmobile.calllogger.data.DataManager
import com.icanmobile.calllogger.data.calllog.CallLog
import com.icanmobile.calllogger.util.disposedBy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

/**
 * Created by JONG HO BAEK on 29,April,2019
 * email: icanmobile@gmail.com
 *
 * CallLoggerActivityViewModel class
 */
class CallLoggerActivityViewModel @Inject constructor(private val dataManager: DataManager): ViewModel() {

    private var bag = CompositeDisposable()

    /**
     * call logs live data
     */
    val callLogs: MutableLiveData<List<CallLog>> by lazy {
        MutableLiveData<List<CallLog>>()
    }

    init {

        /**
         * receive call histories from DataManager class based on reactive extensions.
         */
        dataManager.callLogs
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { logs ->
                callLogs.setValue(logs)
            }
            .disposedBy(bag)
    }

    override fun onCleared () {
        bag.clear()
    }

    /**
     * load call histories from DataManager
     * @param contentResolver the content resolver
     * @param limit the max number of call histories
     */
    fun loadCallLogs(@NonNull contentResolver: ContentResolver, limit: Int) {
        dataManager.loadCallLogs(contentResolver, limit)
    }
}