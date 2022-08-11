package com.example.planner

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.planner.AddTasks.Companion.getIsDone
import com.example.planner.AddTasks.Companion.getList
import com.example.planner.AddTasks.Companion.saveIsDone
import com.example.planner.AddTasks.Companion.saveList
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Text
import java.lang.reflect.Type


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    var tasksList: ListView? = null

    var arrayAdapter: ArrayAdapter<String>? = null
    val arrayList: MutableList<String> = mutableListOf()
    val doneList: MutableList<Boolean> = mutableListOf()


    var isLongPress = false

    override fun onResume() {
        super.onResume()

        val sharedPreferences = getSharedPreferences("com.example.acer.notepad", MODE_PRIVATE)

        val existingTasks = getList(sharedPreferences)
        val existingFlags = getIsDone(sharedPreferences)

        if (existingTasks.isEmpty()) {
            //
        } else {
            arrayList.clear()
            arrayList.addAll(existingTasks)
        }

        tasksList?.setOnItemLongClickListener(AdapterView.OnItemLongClickListener { parent, view, p, id ->
            isLongPress = true
            val alertDialog = AlertDialog.Builder(this@MainActivity)
                .setTitle("Delete task")
                .setMessage("Are you sure you want to delete it?")
                .setPositiveButton("Yes") { dialog, which ->
                    //get current list
                    //val currentList = getList(sharedPreferences)
                    //remove note from existing list
                    existingTasks.removeAt(p)
                    existingFlags.removeAt(p)
                    arrayAdapter?.notifyDataSetChanged()
                    //save new list to sharedPreferences
                    saveList(sharedPreferences, existingTasks)
                    saveIsDone(sharedPreferences, existingFlags)
                    val refresh = Intent(this, MainActivity::class.java)
                    startActivity(refresh)
                    finish()
                    Toast.makeText(this@MainActivity, "Task deleted", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton(
                    "No"
                ) { dialog, which -> dialog.dismiss() }
                .create()
            alertDialog.show()
            isLongPress = false
            true
        })

        tasksList?.setOnItemClickListener { parent, view, p, id ->
            val alertDialog = AlertDialog.Builder(this@MainActivity)
                .setTitle("Done task")
                .setMessage("Is the task finished?")
                .setPositiveButton("Yes") { dialog, which ->
                    existingFlags[p] = true
                    val taskText = view.findViewById<TextView>(android.R.id.text1)
                    taskText.paintFlags = taskText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    existingTasks.removeAt(p)
                    existingFlags.removeAt(p)
                    arrayAdapter?.notifyDataSetChanged()
                    saveList(sharedPreferences, existingTasks)
                    saveIsDone(sharedPreferences, existingFlags)
                    val refresh = Intent(this, MainActivity::class.java)
                    startActivity(refresh)
                    finish()
                    Toast.makeText(this@MainActivity, "Task done", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton(
                    "No"
                ) { dialog, which -> dialog.dismiss() }
                .create()
            alertDialog.show()
        }

        arrayAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList)
        tasksList?.setAdapter(arrayAdapter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("com.example.acer.notepad", MODE_PRIVATE)

        tasksList = findViewById(R.id.TaskList)

        val button: Button = findViewById(R.id.AddTask)
        button.setOnClickListener {
            val intent = Intent(this, AddTasks::class.java)
            startActivity(intent)
        }

        // Initialize and assign variable
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Set Home selected
        bottomNavigationView.selectedItemId = R.id.tasks

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.calendar -> {
                    val a = Intent(applicationContext, Calendar::class.java)
                    //a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(a)
                    finish()
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.tasks -> return@OnNavigationItemSelectedListener true
                R.id.notes -> {
                    val b = Intent(applicationContext, Notes::class.java)
                    //b.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(b)
                    finish()
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })

    }
}




