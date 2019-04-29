package com.icanmobile.calllogger.ui.calllogger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.icanmobile.calllogger.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_call_logger.*
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


        initUI()
    }


    //region UI
    private lateinit var adapter: CallLoggerAdapter

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
}
