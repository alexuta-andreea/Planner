package com.example.planner

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemLongClickListener
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

@Suppress("DEPRECATION")
class Notes : AppCompatActivity() {

    var notesList: ListView? = null

    var arrayAdapter: ArrayAdapter<String>? = null
    val arrayList: MutableList<String> = mutableListOf()

    var isLongPress = false

    var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        notesList = findViewById(R.id.NoteList)

        sharedPreferences = getSharedPreferences("com.example.acer.notepad", MODE_PRIVATE)
        sharedPreferences = getPreferences(MODE_PRIVATE)
        val noteNo = sharedPreferences?.getInt("NoteNo", -1)
        if (noteNo != -1) {
            for (x in 0..noteNo!!) {
                arrayList.add(
                    x,
                    sharedPreferences?.getString(x.toString(), "Press to add new notes").toString()
                )
            }
        } else {
            arrayList.add(0,"Press to add new notes")
        }
        arrayAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList)
        notesList?.setAdapter(arrayAdapter)

        CheckEmptyNote()

//        notesList?.setOnItemLongClickListener(OnItemLongClickListener { parent, view, p, id ->
//            isLongPress = true
//            val alertDialog = AlertDialog.Builder(this@Notes)
//                .setIcon(R.drawable.ic_launcher_foreground)
//                .setTitle("Delete note")
//                .setMessage("Are you sure you want to delete it")
//                .setPositiveButton("Yes") { dialog, which ->
//                    arrayList.removeAt(p)
//                    arrayAdapter.notifyDataSetChanged()
//                    Toast.makeText(this@Notes, "Note deleted", Toast.LENGTH_SHORT).show()
//                    for (x in p until arrayList.size) {
//                        sharedPreferences.edit()
//                            .putString(x.toString(), arrayList.get(x))
//                            .apply()
//                    }
//                    sharedPreferences.edit().putInt("NoteNo", arrayList.size - 1)
//                        .apply()
//                }
//                .setNegativeButton(
//                    "No"
//                ) { dialog, which -> dialog.dismiss() }
//                .create()
//            alertDialog.show()
//            isLongPress = false
//            true
//        })
//        if (!isLongPress) {
//            notesList.setOnItemClickListener(OnItemClickListener { parent, view, p, id ->
//                val intent = Intent(applicationContext, AddNotes::class.java)
//                intent.putExtra("index", p)
//                startActivity(intent)
//            })
//        }

        //val addNote = findViewById<Button>(R.id.AddNote)

        //addNote.setOnClickListener {
            //startActivity(Intent(applicationContext, AddNotes::class.java))
        //}

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

    fun CheckEmptyNote() {
        for (i in arrayList.indices) {
            if (arrayList.get(i).isEmpty()) {
                arrayList.set(i, "Press to add new notes")
            }
        }
    }

//    fun AddNewNote(view: View?) {
//        arrayList.set(arrayList.size, "Press to add new notes")
//        arrayAdapter.notifyDataSetChanged()
//        val intent = Intent(applicationContext, AddNotes::class.java)
//        intent.putExtra("index", arrayList.size - 1)
//        startActivity(intent)
//    }
}
