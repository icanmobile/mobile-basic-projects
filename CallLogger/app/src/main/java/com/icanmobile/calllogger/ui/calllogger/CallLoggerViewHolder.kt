package com.icanmobile.calllogger.ui.calllogger

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.icanmobile.calllogger.R
import com.icanmobile.calllogger.data.calllog.CallLog

/**
 * Created by JONG HO BAEK on 29,April,2019
 * email: icanmobile@gmail.com
 *
 * CallLoggerViewHolder class
 */
class CallLoggerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val numberTextView: TextView
    val dateTextView: TextView
    val typeTextView: TextView

    init {
        numberTextView = itemView.findViewById(R.id.numberTextView)
        dateTextView = itemView.findViewById(R.id.dateTextView)
        typeTextView = itemView.findViewById(R.id.typeTextView)
    }

    fun configureWith(callLog: CallLog) {
        numberTextView.text = callLog.callNumber
        dateTextView.text = callLog.callDate
        typeTextView.text = callLog.callType
    }
}