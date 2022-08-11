package com.example.planner

import android.view.View
import android.widget.TextView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.ViewContainer

class DayViewContainer(view: View) : ViewContainer(view) {
    lateinit var day: CalendarDay
    val textView = view.findViewById<TextView>(R.id.exThreeDayText)
}

