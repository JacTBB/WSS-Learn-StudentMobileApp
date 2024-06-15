package com.example.studentmobileapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.studentmobileapp.Models.Student
import com.example.studentmobileapp.Utilities.GetCourseListHttpService
import com.example.studentmobileapp.Utilities.GetStudentListHttpService
import com.example.studentmobileapp.Utilities.Settings
import com.example.studentmobileapp.databinding.FragmentFirstBinding
import com.google.android.material.snackbar.Snackbar


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GetStudentListHttpService(this).execute()
        GetCourseListHttpService(this).execute()
        (requireActivity() as MainActivity).title = "List of Students"
        binding.studentListview.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            // This is your listview's selected item
            val item = parent.getItemAtPosition(position)
            Settings.AddStudent=false
            Settings.studentObj=Settings.studentList.get(position)
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    public fun onGetListCompleted(studentList: MutableList<Student>) {
        var names = ArrayList<String>()
        val states = ArrayList<String>()
        for (i in 0 until studentList.size) {
            names.add(studentList[i].modifiedName)
            states.add(studentList[i].state)
        }
        val myListAdapter = StudentListAdapter(requireActivity() as MainActivity, names, states)
        binding.studentListview.adapter = myListAdapter
        (requireActivity() as MainActivity).binding.fab.isVisible=true
    }
    public fun onGetListError() {
        Snackbar.make(View(requireActivity() as MainActivity), "An error has occurred. Please contact app support for assistance.", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }
}