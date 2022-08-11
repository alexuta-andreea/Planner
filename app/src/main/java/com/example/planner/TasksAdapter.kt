package com.example.planner

import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class TasksAdapter: BaseAdapter() {

    val taskList: MutableList<Task> = mutableListOf()

    override fun getCount(): Int {
        return taskList.size
    }

    override fun getItem(p0: Int): Any {
        return taskList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(index: Int, view: View, parent: ViewGroup?): View {

        val taskText = view.findViewById<TextView>(android.R.id.text1)
        taskText.text = taskList[index].text
        taskText.paintFlags = taskText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        return view
    }

}

data class Task(val text: String, var isDone: Boolean)