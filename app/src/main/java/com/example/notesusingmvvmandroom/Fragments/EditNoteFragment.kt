package com.example.notesusingmvvmandroom.Fragments

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.notesusingmvvmandroom.Adapter.MyAdapter
import com.example.notesusingmvvmandroom.R
import com.example.notesusingmvvmandroom.ViewModel.ViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class EditNoteFragment : Fragment() {

    private lateinit var noteViewModel:ViewModel
    private lateinit var navController:NavController
    private lateinit var titleView:EditText
    private lateinit var noteView:EditText
    private lateinit var btn:FloatingActionButton
    private var noteId:Int?=null
   // private lateinit var adapter:MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_edit, menu)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_note, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view)
        noteViewModel=ViewModelProvider(this).get(ViewModel::class.java)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        btn=view.findViewById(R.id.EditNoteFab_done)
        titleView=view.findViewById(R.id.Edit_Note_Heading)
        noteView=view.findViewById(R.id.Edit_Note_text)
        noteId=arguments?.get("id") as Int
        noteId?.let {
            noteViewModel.getNoteById(it).observe(viewLifecycleOwner) { note ->
            note?.let{
                titleView.text=Editable.Factory.getInstance().newEditable(it.title)
                noteView.text=Editable.Factory.getInstance().newEditable(it.notes)
            }

            }
        }
        btn.setOnClickListener {
            val title=titleView.text.toString()
            val note=noteView.text.toString()
            noteViewModel.update(com.example.notesusingmvvmandroom.Model.Note(noteId!!,title,note))
            navController.navigateUp()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // Check if NavController can navigate back
            if (navController.popBackStack()) {
                // Navigation was successful
                navController.navigate(R.id.action_editNoteFragment_to_homeFragment)
            } else {
                // If back stack is empty, exit the app or go to previous activity
                requireActivity().finish()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.deleteMenu->{
                noteViewModel.delete(com.example.notesusingmvvmandroom.Model.Note(noteId!!,"","",))
                navController.navigateUp()
                true
            }
            android.R.id.home -> {
                navController.navigateUp() // This automatically handles back navigation
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }

}