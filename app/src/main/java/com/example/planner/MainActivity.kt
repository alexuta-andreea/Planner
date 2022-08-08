package com.example.planner

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Type

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

//    private var dataModel: ArrayList<DataModel>? = null
//    //private lateinit var listView: ListView
//    //private lateinit var adapter: CustomAdapter

    var tasksList: ListView? = null

    var arrayAdapter: ArrayAdapter<String>? = null
    val arrayList: MutableList<String> = mutableListOf()

    var isLongPress = false

    override fun onResume() {
        super.onResume()

        val sharedPreferences = getSharedPreferences("com.example.acer.notepad", MODE_PRIVATE)

        val existingTasks = getList(sharedPreferences)

        if(existingTasks.isEmpty()) {
            arrayList.add("Press to add new tasks")
        } else {
            arrayList.clear()
            arrayList.addAll(existingTasks)
        }

        /////////////////////////////////////////

        tasksList?.setOnItemLongClickListener(AdapterView.OnItemLongClickListener { parent, view, p, id ->
            isLongPress = true
            val alertDialog = AlertDialog.Builder(this@MainActivity)
                .setTitle("Delete task")
                .setMessage("Are you sure you want to delete it?")
                .setPositiveButton("Yes") { dialog, which ->
                    //get current list
                    val currentList = getList(sharedPreferences)
                    //remove note from existing list
                    currentList.removeAt(p)
                    arrayAdapter?.notifyDataSetChanged()
                    //save new list to sharedPreferences
                    saveList(sharedPreferences, currentList)
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

        /////////////////////////////////////////
        arrayAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList)
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
                    val a =Intent(applicationContext, Calendar::class.java)
                    //a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(a)
                    finish()
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.tasks -> return@OnNavigationItemSelectedListener true
                R.id.notes -> {
                    val b =Intent(applicationContext, Notes::class.java)
                    //b.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(b)
                    finish()
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })


//        //
//        tasksList?.adapter = arrayAdapter
//        tasksList?.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
//            val dataModel: DataModel = dataModel!![position] as DataModel
//            dataModel.checked = !dataModel.checked
//            arrayAdapter?.notifyDataSetChanged()
//        }
//
//        //
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

    }
}
