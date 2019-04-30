package com.icanmobile.calllogger.ui.calllogger

import android.content.ContentResolver
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.icanmobile.calllogger.data.DataManager
import com.icanmobile.calllogger.data.calllog.CallLog
import com.icanmobile.calllogger.util.Constants
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
                merge(logs.toMutableList())
            }
            .disposedBy(bag)
    }

    override fun onCleared () {
        bag.clear()
    }


    private var deque: Deque<CallLog> = LinkedList()
    private var mLimit: Int = Constants.LIMIT

    /**
     * merge the call histories using Deque.
     * If deque has data, we add the updated call histories to Deque.
     * If deque has over 50 call histories, remove last call histories from Deque.
     * @param logs the call histories from loadCallLogs() method or updateCallLogs() methods.
     */
    private fun merge(logs: List<CallLog>) {
        if (logs.isEmpty()) return

        if (deque.isEmpty()) {
            logs.forEach { log ->
                deque.addLast(log)
            }
        }
        else {
            var acendingOrderLogs = logs.reversed()
            acendingOrderLogs.forEach { log ->
                deque.addFirst(log)
                if (deque.size > mLimit)
                    deque.removeLast()
            }
        }
        callLogs.value = deque.toMutableList()
    }


    /**
     * load call histories from DataManager
     * @param contentResolver the content resolver
     * @param limit the max number of call histories
     */
    fun loadCallLogs(@NonNull contentResolver: ContentResolver, limit: Int) {
        // if deque has call histories, we just update CallLoggerActivity UI.
        // ex) change the screen orientation.
        if (!deque.isEmpty()) {
            callLogs.value = deque.toMutableList()
            return
        }

        mLimit = limit
        dataManager.loadCallLogs(contentResolver, limit)
    }


    /**
     * update call histories from DataManager
     * @param contentResolver the content resolver
     */
    fun updateCallLogs(contentResolver: ContentResolver) {
        dataManager.updateCallLogs(contentResolver)
    }
}