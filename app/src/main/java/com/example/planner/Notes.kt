package com.example.planner

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

@Suppress("DEPRECATION")
class Notes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

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
}
