package com.example.planner

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_todo.view.*

class TaskAdapter(
    private val tasks: MutableList<Task>
) : RecyclerView.Adapter<TaskAdapter.TodoViewHolder>() {

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_todo,
                parent,
                false
            )
        )
    }

    fun addTodo(task: Task) {
        tasks.add(task)
        notifyItemInserted(tasks.size - 1)
    }

    fun deleteDoneTodos() {
        tasks.removeAll { todo ->
            todo.isChecked
        }
        notifyDataSetChanged()
    }

    private fun toggleStrikeThrough(TaskName: TextView, isChecked: Boolean) {
        if(isChecked) {
            TaskName.paintFlags = TaskName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            TaskName.paintFlags = TaskName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val curTodo = tasks[position]
        holder.itemView.apply {

            TaskName.text = curTodo.title
            Check.isChecked = curTodo.isChecked
            toggleStrikeThrough(TaskName, curTodo.isChecked)
            Check.setOnCheckedChangeListener { _, isChecked ->
                toggleStrikeThrough(TaskName, isChecked)
                curTodo.isChecked = !curTodo.isChecked
            }
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}