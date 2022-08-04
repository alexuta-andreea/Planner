package com.example.planner

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.*
import android.widget.AdapterView.OnItemLongClickListener
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@Suppress("DEPRECATION")
class Notes : AppCompatActivity() {

    var notesList: ListView? = null

    var arrayAdapter: ArrayAdapter<String>? = null
    val arrayList: MutableList<String> = mutableListOf()

    var isLongPress = false

    override fun onResume() {
        super.onResume()

        val sharedPreferences = getSharedPreferences("com.example.acer.notepad", MODE_PRIVATE)

        val existingNotes = getList(sharedPreferences)

        if(existingNotes.isEmpty()) {
            arrayList.add("Press to add new notes")
        } else {
            arrayList.clear()
            arrayList.addAll(existingNotes)
        }

        /////////////////////////////////////////

        notesList?.setOnItemLongClickListener(OnItemLongClickListener { parent, view, p, id ->
            isLongPress = true
            val alertDialog = AlertDialog.Builder(this@Notes)
                .setTitle("Delete note")
                .setMessage("Are you sure you want to delete it?")
                .setPositiveButton("Yes") { dialog, which ->
                    //get current list
                    val currentList = getList(sharedPreferences)
                    //remove note from existing list
                    currentList.removeAt(p)
                    arrayAdapter?.notifyDataSetChanged()
                    //save new list to sharedPreferences
                    saveList(sharedPreferences, currentList)
                    val refresh = Intent(this, Notes::class.java)
                    startActivity(refresh)
                    finish()
                    Toast.makeText(this@Notes, "Note deleted", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton(
                    "No"
                ) { dialog, which -> dialog.dismiss() }
                .create()
            alertDialog.show()
            isLongPress = false
            true
        })

        /////////////////////////////////////////
        arrayAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList)
        notesList?.setAdapter(arrayAdapter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        val sharedPreferences = getSharedPreferences("com.example.acer.notepad", MODE_PRIVATE)

        notesList = findViewById(R.id.NoteList)

        val button: Button = findViewById(R.id.AddNote)
        button.setOnClickListener {
            val intent = Intent(this, AddNotes::class.java)
            startActivity(intent)
        }

        // Initialize and assign variable
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Set Home selected
        bottomNavigationView.selectedItemId = R.id.notes

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
                R.id.notes -> return@OnNavigationItemSelectedListener true
                R.id.tasks -> {
                    val b = Intent(applicationContext, MainActivity::class.java)
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

        //this.recreate();

        //finish()
        //overridePendingTransition(0, 0);
        //startActivity(getIntent());
        //overridePendingTransition(0, 0);

        //onBackPressed()
    }

//    fun CheckEmptyNote() {
//        for (i in arrayList.indices) {
//            if (arrayList.get(i).isEmpty()) {
//                arrayList.set(i, "Press to add new notes")
//            }
//        }
//    }

}