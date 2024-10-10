package com.example.notesusingmvvmandroom.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.notesusingmvvmandroom.Model.Note
import com.example.notesusingmvvmandroom.R
import com.example.notesusingmvvmandroom.ViewModel.ViewModel


class AddNoteFragment : Fragment() {

    private lateinit var noteTitle: EditText
    private lateinit var notetext:EditText
    private lateinit var navController: NavController
    private lateinit var noteViewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_add, menu)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_note, container, false)

        // Initialize UI elements after inflation
        noteTitle = view.findViewById(R.id.Note_Heading)
        notetext = view.findViewById(R.id.Note_text)

        // Initialize ViewModel
        noteViewModel = ViewModelProvider(this).get(ViewModel::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.saveMenu->{
                saveNote()
                navController.navigateUp()
                true

            }
//            android.R.id.home->{
//                navController.navigate(R.id.action_addNoteFragment2_to_homeFragment)
//            }
            android.R.id.home -> {
                navController.navigateUp() // This automatically handles back navigation
            }


            else -> super.onOptionsItemSelected(item)

        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        // Disable back button when fragment is destroyed
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
    private fun saveNote() {
        val title=noteTitle.text.toString()
        val notesContent=notetext.text.toString()
        if(title.isEmpty() || notesContent.isEmpty()){
            Toast.makeText(requireContext(), "Please enter all the enteries",Toast.LENGTH_LONG).show()
            return
        }
        else{
            val newNote= Note(0,title,notesContent)
            noteViewModel.insert(newNote)
            Toast.makeText(requireContext(), "Note saved successfully", Toast.LENGTH_SHORT).show()
            //requireActivity().onBackPressed()
        }
    }


}