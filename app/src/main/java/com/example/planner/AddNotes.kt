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


class AddNotes : AppCompatActivity() {

    var para: EditText? = null
    var saveBtn: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)

        val sharedPreferences = getSharedPreferences("com.example.acer.notepad", MODE_PRIVATE)
        para = findViewById(R.id.Note)
        saveBtn = findViewById(R.id.saveNoteBtn)

        saveBtn?.setOnClickListener {
            saveNoteToSharedPref(sharedPreferences, para?.text.toString())
        }
    }

    private fun saveNoteToSharedPref(sharedPreferences: SharedPreferences, newNote: String) {
        //get current list
        val currentList = getList(sharedPreferences)

        //add new note to existing list
        currentList.add(newNote)

        //save new list to sharedPreferences
        saveList(sharedPreferences, currentList)

    }

    private fun getList(sharedPreferences: SharedPreferences): MutableList<String> {
        var arrayItems: List<String> = mutableListOf()
        val serializedObject: String? = sharedPreferences.getString("AllNotes", null)
        if (serializedObject != null) {
            val gson = Gson()
            val type: Type = object : TypeToken<List<String?>?>() {}.type
            arrayItems = gson.fromJson(serializedObject, type)
        }

        return arrayItems.toMutableList()
    }

    private fun saveList(sharedPreferences: SharedPreferences, notesList: MutableList<String>) {
        val gson = Gson()
        val newNotesListToSaveAsString = gson.toJson(notesList)

        Log.d("new list", notesList.toString())

        sharedPreferences.edit().putString("AllNotes", newNotesListToSaveAsString).commit()

        onBackPressed()
    }

}