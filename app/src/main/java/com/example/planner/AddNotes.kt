package com.example.planner

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddNotes : AppCompatActivity() {

    var para: EditText? = null
    var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)

        sharedPreferences = getSharedPreferences("com.example.acer.notepad", MODE_PRIVATE)
        para = findViewById(R.id.Note)


            para?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    arrayList.set(index, s.toString())
                    arrayAdapter.notifyDataSetChanged()
                    sharedPreferences.edit()
                        .putString(index.toString(), arrayList.get(index)).apply()
                    sharedPreferences.edit().putInt("NoteNo", index).apply()
                    CheckEmptyNote()
                }

                override fun afterTextChanged(s: Editable) {}
            })
        }

    }