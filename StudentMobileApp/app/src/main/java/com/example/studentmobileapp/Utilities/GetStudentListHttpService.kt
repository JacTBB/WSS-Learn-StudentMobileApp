package com.example.studentmobileapp.Utilities

import android.R
import android.os.AsyncTask
import android.util.Log
import com.example.studentmobileapp.Models.Student
import com.example.studentmobileapp.FirstFragment
import com.example.studentmobileapp.Models.Course
import com.example.studentmobileapp.SecondFragment
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL


class GetStudentListHttpService(private var context:FirstFragment) : AsyncTask<Void, Void, MutableList<Student>>() {
    override fun doInBackground(vararg params: Void?): MutableList<Student>? {
        val url = URL("http://10.0.2.2:5081/api/student")
        val con: HttpURLConnection = url.openConnection() as HttpURLConnection
        con.requestMethod = "GET"
        con.addRequestProperty("Accept", "application/json")

        val status = con.responseCode
        val statusMsg = con.responseMessage
        val reader = BufferedReader(InputStreamReader(con.inputStream))
        val jsonData = StringBuilder()

        if(status==200) {
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                jsonData.append(line)
            }
            reader.close()

            //Read JSON response and print
            val jSONArray = JSONArray(jsonData.toString())
            val studentList = mutableListOf<Student>()
            for (i in 0 until jSONArray.length()) {
                try {
                    val objJson: JSONObject = jSONArray.getJSONObject(i)
                    val obj = Student()

                    obj.studentId = objJson.getInt("studentId")
                    obj.modifiedName = objJson.getString("modifiedStudentName")
                    obj.studentName = objJson.getString("studentName")
                    obj.standardId = objJson.getInt("standardId")
                    obj.address1 = objJson.getString("address1")
                    obj.address2 = objJson.getString("address2")
                    obj.state = objJson.getString("state")
                    obj.city = objJson.getString("city")
                    val coursesObj = objJson.getJSONObject("courses")
                    val jsonArray = coursesObj.getJSONArray("\$values")
                    val list = mutableListOf<Int>()
                    for (i in 0 until jsonArray.length()) {
                        list.add(jsonArray.getInt(i))
                    }
                    obj.courses = list
                    studentList.add(obj)
                } catch (e: Exception) {

                }
                Settings.studentList = studentList
            }
            Log.d("Http Service Tag", "number of students: " + studentList.count());
            return studentList
        }
        else
        {
            return null;
        }

    }

    override fun onPreExecute() {
        super.onPreExecute()
        // ...
    }

    override fun onPostExecute(result: MutableList<Student>) {
        super.onPostExecute(result)
        if(result!=null) {
            context.onGetListCompleted(result);
        }
        else
        {
            context.onGetListError();
        }
    }
}

class AddStudentHttpService(private var context: SecondFragment, private var newStudent:Student) : AsyncTask<Void, Void, Boolean>() {
    override fun doInBackground(vararg params: Void?): Boolean {
        val url = URL("http://10.0.2.2:5081/api/student")
        val con: HttpURLConnection = url.openConnection() as HttpURLConnection
        con.requestMethod = "POST"
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        val json = Json.encodeToString(newStudent)
        val os: OutputStream = con.outputStream
        os.write(json.toByteArray())
        os.flush()
        os.close()
        Log.d("Http Service Tag-post", "response code "+con.responseCode);

        con.disconnect();
        return con.responseCode == 200
    }

    override fun onPreExecute() {
        super.onPreExecute()
        // ...
    }

    override fun onPostExecute(result: Boolean) {
        super.onPostExecute(result)
        if(result)
        {
            context.onAddorUpdateCompleted();
        }
        else
        {
            context.onAddorUpdateError();
        }
    }
}

class EditStudentHttpService(private var context: SecondFragment, private var newStudent:Student) : AsyncTask<Void, Void, Boolean>() {
    override fun doInBackground(vararg params: Void?): Boolean {
        val url = URL("http://10.0.2.2:5081/api/student/${newStudent.studentId}")
        val con: HttpURLConnection = url.openConnection() as HttpURLConnection
        con.requestMethod = "PUT"
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        val json = Json.encodeToString(newStudent)
        val os: OutputStream = con.outputStream
        os.write(json.toByteArray())
        os.flush()
        os.close()
        Log.d("Http Service Tag-post", "response code "+con.responseCode);

        con.disconnect();
        return con.responseCode == 200
    }

    override fun onPreExecute() {
        super.onPreExecute()
        // ...
    }

    override fun onPostExecute(result: Boolean) {
        super.onPostExecute(result)
        if(result)
        {
            context.onAddorUpdateCompleted();
        }
        else
        {
            context.onAddorUpdateError();
        }
    }
}

class DeleteStudentHttpService(private var context: SecondFragment, private var studentId: Int?) : AsyncTask<Void, Void, Boolean>() {
    override fun doInBackground(vararg params: Void?): Boolean {
        val url = URL("http://10.0.2.2:5081/api/student/${studentId}")
        val con: HttpURLConnection = url.openConnection() as HttpURLConnection
        con.requestMethod = "DELETE"
        con.addRequestProperty("Accept", "application/json")
        Log.d("Http Service Tag-post", "response code "+con.responseCode);

        con.disconnect();
        return con.responseCode == 200
    }

    override fun onPreExecute() {
        super.onPreExecute()
        // ...
    }

    override fun onPostExecute(result: Boolean) {
        super.onPostExecute(result)
        if(result)
        {
            context.onAddorUpdateCompleted();
        }
        else
        {
            context.onAddorUpdateError();
        }
    }
}

class GetCourseListHttpService(private var context:FirstFragment) : AsyncTask<Void, Void, MutableList<Course>>() {
    override fun doInBackground(vararg params: Void?): MutableList<Course>? {
        val url = URL("http://10.0.2.2:5081/api/course")
        val con: HttpURLConnection = url.openConnection() as HttpURLConnection
        con.requestMethod = "GET"
        con.addRequestProperty("Accept", "application/json")

        val status = con.responseCode
        val statusMsg = con.responseMessage
        val reader = BufferedReader(InputStreamReader(con.inputStream))
        val jsonData = StringBuilder()

        if(status==200) {
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                jsonData.append(line)
            }
            reader.close()

            //Read JSON response and print
            val jSONArray = JSONArray(jsonData.toString())
            val courseList = mutableListOf<Course>()
            for (i in 0 until jSONArray.length()) {
                try {
                    val objJson: JSONObject = jSONArray.getJSONObject(i)
                    val obj = Course()

                    obj.courseId = objJson.getInt("courseId")
                    obj.courseName = objJson.getString("courseName")
                    obj.teacherId = objJson.getInt("teacherId")
                    val coursesObj = objJson.getJSONObject("students")
                    val jsonArray = coursesObj.getJSONArray("\$values")
                    val list = mutableListOf<Int>()
                    for (i in 0 until jsonArray.length()) {
                        list.add(jsonArray.getInt(i))
                    }
                    obj.students = list
                    courseList.add(obj)
                } catch (e: Exception) {

                }
                Settings.courseList = courseList
            }
            Log.d("Http Service Tag", "number of courses: " + courseList.count());
            return courseList
        }
        else
        {
            return null;
        }

    }

    override fun onPreExecute() {
        super.onPreExecute()
        // ...
    }

    override fun onPostExecute(result: MutableList<Course>) {
        super.onPostExecute(result)
        if(result==null)
        {
            context.onGetListError();
        }
    }
}