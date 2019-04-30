package com.icanmobile.calllogger.util

class Constants {

    companion object {
        const val LIMIT = 50

        var PERMISSIONS = arrayOf(
            android.Manifest.permission.READ_CALL_LOG,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.PROCESS_OUTGOING_CALLS)
    }
}