package com.icanmobile.calllogger.data

import android.content.ContentResolver
import androidx.annotation.NonNull
import com.icanmobile.calllogger.data.calllog.CallLog
import com.icanmobile.calllogger.data.calllog.CallLogManager
import com.jakewharton.rxrelay2.BehaviorRelay
import org.jetbrains.annotations.NonNls
import javax.inject.Inject

/**
 * Created by JONG HO BAEK on 29,April,2019
 * email: icanmobile@gmail.com
 *
 * DataManager class
 */
class DataManager @Inject constructor(private val callLogManager: CallLogManager) {

    val callLogs = BehaviorRelay.createDefault(listOf<CallLog>())

    /**
     * load call histories from CallLogManager class and transfer the histories using reactive extensions.
     * @param contentResolver the content resolver
     * @param limit the max number of call histories
     */
    fun loadCallLogs(@NonNull contentResolver: ContentResolver, limit: Int) {
        callLogManager.loadCallLogs(contentResolver, limit) { logs ->
            callLogs.accept(logs)
        }
    }


    /**
     * update call histories from CallLogManager class and transfer the histories using reactive extensions.
     * @param contentResolver the content resolver
     */
    fun updateCallLogs(contentResolver: ContentResolver) {
        callLogManager.updateCallLogs(contentResolver) { logs ->
            callLogs.accept(logs)
        }
    }
}