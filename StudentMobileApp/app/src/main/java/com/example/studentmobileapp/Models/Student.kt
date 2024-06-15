package com.example.studentmobileapp.Models

import kotlinx.serialization.Serializable

@Serializable
class Student {
    var studentId: Int? = null
    var modifiedName: String = ""
    var studentName: String = ""
    var standardId: Int? = null
    var address1: String = ""
    var address2: String? = null
    var city: String? = null
    var state: String = ""
    var courses: List<Int?> = mutableListOf()
}