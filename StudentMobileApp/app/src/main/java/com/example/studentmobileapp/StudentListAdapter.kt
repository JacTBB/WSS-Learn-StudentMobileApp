package com.example.studentmobileapp

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*

class StudentListAdapter(private val context: Activity, private val title: ArrayList<String>, private val description: ArrayList<String>)
    : ArrayAdapter<String>(context, R.layout.student_listview_row, title) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.student_listview_row, null, true)

        val titleText = rowView.findViewById(R.id.header) as TextView
        val subtitleText = rowView.findViewById(R.id.text) as TextView

        titleText.text = title[position]
        subtitleText.text = description[position]

        return rowView
    }

    override fun getCount(): Int {
        return super.getCount()
    }

    override fun getItem(position: Int): String? {
        return super.getItem(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
}

