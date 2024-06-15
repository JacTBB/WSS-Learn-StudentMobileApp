package com.example.studentmobileapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmobileapp.Models.Course

class CoursesListAdapter(private val options: List<Course>) : RecyclerView.Adapter<CoursesListAdapter.OptionViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_option, parent, false)
        return OptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        val option = options[position]
        holder.bind(option)
    }

    override fun getItemCount(): Int = options.size

    inner class OptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val optionTextView: TextView = itemView.findViewById(R.id.optionTextView)

        fun bind(course: Course) {
            optionTextView.text = "${course.courseName} (${course.students?.size} Students)"
            itemView.setOnClickListener {
                if (course.selectable) {
                    course.isSelected = !course.isSelected
                    notifyDataSetChanged()
                }
            }
            // Change background color based on selection state
            itemView.setBackgroundResource(
                if (course.isSelected) R.color.selected_item_background
                else android.R.color.transparent
            )
        }
    }
}