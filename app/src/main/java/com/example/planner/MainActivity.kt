package com.example.planner

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        taskAdapter = TaskAdapter(mutableListOf())

        rvTask.adapter = taskAdapter
        rvTask.layoutManager = LinearLayoutManager(this)

        AddTask.setOnClickListener {
            val todoTitle = Task.text.toString()
            if(todoTitle.isNotEmpty()) {
                val task = Task(todoTitle)
                taskAdapter.addTodo(task)
                Task.text.clear()
            }
        }
        DeleteDone.setOnClickListener {
            taskAdapter.deleteDoneTodos()
        }

        // Initialize and assign variable
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Set Home selected
        bottomNavigationView.selectedItemId = R.id.tasks

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.calendar -> {
                    startActivity(Intent(applicationContext, Calendar::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.tasks -> return@OnNavigationItemSelectedListener true
                R.id.notes -> {
                    startActivity(Intent(applicationContext, Notes::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
    }
}