package com.icanmobile.calllogger.data.calllog

import java.lang.Long
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by JONG HO BAEK on 29,April,2019
 * email: icanmobile@gmail.com
 *
 * CallLog class
 */
class CallLog(private val number: String, private val date: String, private val type: String) {

    var callNumber = number

    var callDate = SimpleDateFormat(DATE_FORMAT).format(Date(Long.parseLong(date)))

    var callType = INCOMING
        get() {
            if (type == "1") return INCOMING
            else return OUTGOING
        }

    override fun toString(): String {
        var sbuilder = StringBuilder()
        sbuilder.append("callNumber = $callNumber").append(System.lineSeparator())
            .append("callDate = $callDate").append(System.lineSeparator())
            .append("callType = $callType")
        return sbuilder.toString()
    }

    companion object {
        private const val DATE_FORMAT = "h:mm a MM/d/yy"
        private const val INCOMING = "Incoming"
        private const val OUTGOING = "Outgoing"
    }
}