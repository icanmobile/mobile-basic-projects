package com.icanmobile.calllogger.ui.calllogger

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.icanmobile.calllogger.R
import com.icanmobile.calllogger.data.calllog.CallLog
import com.icanmobile.calllogger.util.Constants.Companion.LIMIT
import com.icanmobile.calllogger.util.Constants.Companion.PERMISSIONS
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_call_logger.*
import java.util.HashMap
import javax.inject.Inject




/**
 * Created by JONG HO BAEK on 29,April,2019
 * email: icanmobile@gmail.com
 *
 * CallLoggerActivity class
 */
class CallLoggerActivity : DaggerAppCompatActivity() {

    @Inject lateinit var callLoggerActivityViewModelFactory: CallLoggerActivityViewModelFactory
    private lateinit var callLoggerActivityViewModel: CallLoggerActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_logger)

        callLoggerActivityViewModel = ViewModelProviders.of(this, callLoggerActivityViewModelFactory)
            .get(CallLoggerActivityViewModel::class.java)

        callLoggerActivityViewModel.callLogs.observe(this,
            Observer<List<CallLog>> { logs ->
                adapter.callLogs.accept(logs.toMutableList())
        })

        initUI()

        if (!hasPermissions(this, *PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL)
        }
        else {
            loadCallLogs()
        }
    }



    //region UI
    private lateinit var adapter: CallLoggerAdapter
    /**
     * init user interfaces
     */
    private fun initUI() {
        val linearLayoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.addItemDecoration(dividerItemDecoration)

        adapter = CallLoggerAdapter { view, position -> itemClicked(position) }
        recyclerView.adapter = adapter
    }
    private fun itemClicked(position: Int) {
        println(adapter.callLogs.value[position])
    }
    //endregion



    //region communication methods with CallLoggerActivityViewModel class
    /**
     * load call histories from CallLoggerActivityViewModel class
     */
    fun loadCallLogs() {
        callLoggerActivityViewModel.loadCallLogs(contentResolver, LIMIT)
    }
    //endregion



    //region permissions
    private val PERMISSION_ALL = 101
    /**
     * check the permissions.
     * @param context the context
     * @param permissions the permissions
     * @return true/false whether application has permissions or not
     */
    fun hasPermissions(context: Context?, vararg permissions: String): Boolean {
        if (context != null) {
            permissions.forEach {
                if (ActivityCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
        }
        return true
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_ALL -> {
                // Using HashMap to save each permission condition.
                val perms = HashMap<String, Int>()
                perms.put(Manifest.permission.READ_CALL_LOG, PackageManager.PERMISSION_GRANTED)
                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED)
                perms.put(Manifest.permission.PROCESS_OUTGOING_CALLS, PackageManager.PERMISSION_GRANTED)

                if (grantResults.size > 0) {
                    for (i in permissions.indices)
                        perms.put(permissions[i], grantResults[i])

                    if (perms.get(Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED &&
                        perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED &&
                        perms.get(Manifest.permission.PROCESS_OUTGOING_CALLS) == PackageManager.PERMISSION_GRANTED) {

                        // load call histories
                        loadCallLogs()
                    } else {
                        // finish this activity
                        finish()
                    }
                }
            }
        }
    }
    //endregion
}
