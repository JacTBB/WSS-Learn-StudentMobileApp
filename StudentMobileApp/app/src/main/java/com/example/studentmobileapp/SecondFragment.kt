package com.example.studentmobileapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentmobileapp.Models.Student
import com.example.studentmobileapp.Utilities.AddStudentHttpService
import com.example.studentmobileapp.Utilities.DeleteStudentHttpService
import com.example.studentmobileapp.Utilities.EditStudentHttpService
import com.example.studentmobileapp.Utilities.Settings
import com.example.studentmobileapp.Utilities.Settings.Companion.courseList
import com.example.studentmobileapp.databinding.FragmentSecondBinding
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    //val msgBox = Snackbar.make(View(requireActivity() as MainActivity),"This is a simple Snackbar",Snackbar.LENGTH_LONG)
    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val adapter = CoursesListAdapter(Settings.courseList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)

        val layoutManager = LinearLayoutManager(context)
        binding.optionsRecyclerView.layoutManager = layoutManager
        binding.optionsRecyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).binding.fab.isVisible=false
        if(Settings.AddStudent)
        {
            (requireActivity() as MainActivity).title = "Add New Student"
            binding.studentNameET.setText("")
            binding.gradeSpin.setSelection(0)
            binding.address1ET.setText("")
            binding.address2ET.setText("")
            binding.stateET.setText("")
            binding.cityET.setText("")

            binding.studentNameET.isEnabled=true
            binding.gradeSpin.isEnabled=true
            binding.address1ET.isEnabled=true
            binding.address2ET.isEnabled=true
            binding.stateET.isEnabled=true
            binding.cityET.isEnabled=true
            binding.optionsRecyclerView.isEnabled=true
            binding.editBtn.isVisible=false
            binding.deleteBtn.isVisible=false
            binding.submitBtn.isVisible=true

            binding.submitBtn.setOnClickListener { view ->
                val newStudentObj = Student()
                newStudentObj.studentName = binding.studentNameET.text.toString()
                newStudentObj.standardId = binding.gradeSpin.selectedItemPosition
                newStudentObj.address1 = binding.address1ET.text.toString()
                newStudentObj.address2 = binding.address2ET.text.toString()
                newStudentObj.state = binding.stateET.text.toString()
                newStudentObj.city = binding.cityET.text.toString()
                val selectedCourses = Settings.courseList.filter { it.isSelected }
                val selectedCoursesIds = selectedCourses.map { it.courseId }
                newStudentObj.courses = selectedCoursesIds
                AddStudentHttpService(this,newStudentObj).execute()
            }
        }
        else {
            (requireActivity() as MainActivity).title = "View Student profile"
            binding.studentNameET.setText(Settings.studentObj.studentName)
            binding.gradeSpin.setSelection(Settings.studentObj.standardId ?: 0)
            binding.address1ET.setText(Settings.studentObj.address1)
            binding.address2ET.setText(Settings.studentObj.address2)
            binding.stateET.setText(Settings.studentObj.state)
            binding.cityET.setText(Settings.studentObj.city)
            for (c in Settings.studentObj.courses) {
                Settings.courseList.firstOrNull { it.courseId == c }?.isSelected = true
            }

            binding.studentNameET.isEnabled=false
            binding.gradeSpin.isEnabled=false
            binding.address1ET.isEnabled=false
            binding.address2ET.isEnabled=false
            binding.stateET.isEnabled=false
            binding.cityET.isEnabled=false
            binding.optionsRecyclerView.isEnabled=false
            for (c in Settings.courseList) {
                c.selectable = false
            }
            binding.deleteBtn.isVisible=false
            binding.submitBtn.isVisible=false

            binding.editBtn.setOnClickListener { view ->
                if (binding.deleteBtn.isVisible) {
                    (requireActivity() as MainActivity).title = "View Student profile"
                    binding.studentNameET.isEnabled=false
                    binding.gradeSpin.isEnabled=false
                    binding.address1ET.isEnabled=false
                    binding.address2ET.isEnabled=false
                    binding.stateET.isEnabled=false
                    binding.cityET.isEnabled=false
                    binding.optionsRecyclerView.isEnabled=false
                    for (c in Settings.courseList) {
                        c.selectable = false
                    }
                    binding.deleteBtn.isVisible=false
                    binding.submitBtn.isVisible=false
                }
                else {
                    (requireActivity() as MainActivity).title = "Edit Student profile"
                    binding.studentNameET.isEnabled=true
                    binding.gradeSpin.isEnabled=true
                    binding.address1ET.isEnabled=true
                    binding.address2ET.isEnabled=true
                    binding.stateET.isEnabled=true
                    binding.cityET.isEnabled=true
                    binding.optionsRecyclerView.isEnabled=true
                    for (c in Settings.courseList) {
                        c.selectable = true
                    }
                    binding.deleteBtn.isVisible=true
                    binding.submitBtn.isVisible=true
                }
            }

            binding.submitBtn.setOnClickListener { view ->
                val newStudentObj = Student()
                newStudentObj.studentId = Settings.studentObj.studentId
                newStudentObj.studentName = binding.studentNameET.text.toString()
                newStudentObj.standardId = binding.gradeSpin.selectedItemPosition
                newStudentObj.address1 = binding.address1ET.text.toString()
                newStudentObj.address2 = binding.address2ET.text.toString()
                newStudentObj.state = binding.stateET.text.toString()
                newStudentObj.city = binding.cityET.text.toString()
                val selectedCourses = Settings.courseList.filter { it.isSelected }
                val selectedCoursesIds = selectedCourses.map { it.courseId }
                newStudentObj.courses = selectedCoursesIds
                EditStudentHttpService(this, newStudentObj).execute()
            }

            binding.deleteBtn.setOnClickListener { view ->
                DeleteStudentHttpService(this, Settings.studentObj.studentId).execute()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    public fun onAddorUpdateCompleted() {
        findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
    }

    public fun onAddorUpdateError() {
        Snackbar.make(View(requireActivity() as MainActivity), "An error has occurred. Please contact app support for assistance.", Snackbar.LENGTH_LONG)
        .setAction("Action", null).show()
    }
}