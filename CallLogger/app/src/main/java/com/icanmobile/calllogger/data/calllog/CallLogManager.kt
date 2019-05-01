package com.icanmobile.calllogger.data.calllog

import android.content.ContentResolver
import android.net.Uri
import androidx.annotation.NonNull
import com.icanmobile.calllogger.util.Constants.Companion.LIMIT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by JONG HO BAEK on 29,April,2019
 * email: icanmobile@gmail.com
 *
 * CallLogManager class
 */

typealias listener = (List<CallLog>) -> Unit

class CallLogManager {

    private var latestDate: String? = null

    companion object {
        private const val ORDER = " DESC"
    }

    /**
     * load call histories from ContentResolver
     * and transfer the histories to DataManager class.
     * @param contentResolver the content resolver
     * @param limit the max number of call histories
     * @param finished the call back method to return the call histories
     */
    fun loadCallLogs(@NonNull contentResolver: ContentResolver, limit: Int, @NonNull finished: listener) {
        CoroutineScope(Dispatchers.Main).launch {
            val strOrder = android.provider.CallLog.Calls.DATE + ORDER
            val cursor = contentResolver.query(android.provider.CallLog.Calls.CONTENT_URI, null, null, null, strOrder)

            var callLogs = arrayListOf<CallLog>()
            if (cursor != null) {
                while (cursor.moveToNext() && callLogs.size <= limit) {
                    val number = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.NUMBER))
                    val date = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.DATE))
                    val type = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.TYPE))

                    callLogs.add(CallLog(number, date, type))

                    // save the date of latest call log for updating call logs
                    if (callLogs.size == 1) latestDate = date
                }
                cursor.close()

                finished(callLogs)
            }
        }
    }


    /**
     * get call histories from ContentResolver after previous load call logs
     * and transfer the histories to DataManager class.
     * @param contentResolver the content resolver
     * @param finished the call back method to return the call histories
     */
    fun updateCallLogs(contentResolver: ContentResolver, finished: listener) {
        if (latestDate == null) {
            loadCallLogs(contentResolver, LIMIT, finished)
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            val strOrder = android.provider.CallLog.Calls.DATE + ORDER
            val selection = android.provider.CallLog.Calls.DATE + " > ?"
            val selectionArgs = arrayOf(latestDate)
            val cursor = contentResolver.query(android.provider.CallLog.Calls.CONTENT_URI, null, selection, selectionArgs, strOrder)

            var callLogs = arrayListOf<CallLog>()
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val number = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.NUMBER))
                    val date = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.DATE))
                    val type = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.TYPE))

                    callLogs.add(CallLog(number, date, type))

                    // save the date of latest call log for updating call logs
                    if (callLogs.size == 1) latestDate = date
                }
                cursor.close()

                finished(callLogs)
            }
        }
    }
}