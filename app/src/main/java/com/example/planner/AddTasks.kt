package com.example.planner

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class AddTasks : AppCompatActivity()  {

    var para: EditText? = null
    var saveBtn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tasks)

        val sharedPreferences = getSharedPreferences("com.example.acer.notepad", MODE_PRIVATE)
        para = findViewById(R.id.task)
        saveBtn = findViewById(R.id.saveTaskBtn)

        saveBtn?.setOnClickListener {
            saveTaskToSharedPref(sharedPreferences, para?.text.toString())
        }
    }

    private fun saveTaskToSharedPref(sharedPreferences: SharedPreferences, newTask: String) {
        //get current list
        val currentList = getList(sharedPreferences)

        //add new note to existing list
        currentList.add(newTask)

        //save new list to sharedPreferences
        saveList(sharedPreferences, currentList)

    }

    private fun getList(sharedPreferences: SharedPreferences): MutableList<String> {
        var arrayItems: List<String> = mutableListOf()
        val serializedObject: String? = sharedPreferences.getString("AllTasks", null)
        if (serializedObject != null) {
            val gson = Gson()
            val type: Type = object : TypeToken<List<String?>?>() {}.type
            arrayItems = gson.fromJson(serializedObject, type)
        }

        return arrayItems.toMutableList()
    }

    private fun saveList(sharedPreferences: SharedPreferences, tasksList: MutableList<String>) {
        val gson = Gson()
        val newTasksListToSaveAsString = gson.toJson(tasksList)

        Log.d("new list", tasksList.toString())

        sharedPreferences.edit().putString("AllTasks", newTasksListToSaveAsString).commit()

        onBackPressed()
    }

}