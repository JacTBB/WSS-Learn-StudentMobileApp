package com.example.studentmobileapp.Models

import kotlinx.serialization.Serializable

@Serializable
class Course {
    var courseId: Int? = null
    var courseName: String = ""
    var teacherId: Int? = null
    var isSelected: Boolean = false
    var students: List<Int>? = mutableListOf()
    var selectable: Boolean = false
}