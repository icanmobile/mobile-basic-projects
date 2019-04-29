package com.icanmobile.calllogger.ui.calllogger

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.icanmobile.calllogger.R
import com.icanmobile.calllogger.data.calllog.CallLog
import com.icanmobile.calllogger.util.disposedBy
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

typealias ItemClicked = (v: View, position: Int) -> Unit

/**
 * Created by JONG HO BAEK on 29,April,2019
 * email: icanmobile@gmail.com
 *
 * CallLoggerAdapter class
 */
class CallLoggerAdapter(var onItemClicked: ItemClicked): RecyclerView.Adapter<CallLoggerViewHolder>() {

    var callLogs = BehaviorRelay.createDefault(mutableListOf<CallLog>())
    private val bag = CompositeDisposable()

    init {
        callLogs.observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                notifyDataSetChanged()
            }
            .disposedBy(bag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallLoggerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_call_log, parent, false)
        val viewHolder = CallLoggerViewHolder(view)

        view.setOnClickListener { v -> onItemClicked(v, viewHolder.adapterPosition) }

        return viewHolder
    }

    override fun onBindViewHolder(holder: CallLoggerViewHolder, position: Int) {
        val photoDescription = callLogs.value[position]
        holder.configureWith(photoDescription)
    }

    override fun getItemCount(): Int = callLogs.value.size
}