package com.example.studentmobileapp.Utilities

import com.example.studentmobileapp.Models.Course
import com.example.studentmobileapp.Models.Student

class Settings {
    companion object {
        var AddStudent = true
        lateinit var studentObj:Student
        lateinit var studentList:List<Student>
        lateinit var courseList:List<Course>
    }
}