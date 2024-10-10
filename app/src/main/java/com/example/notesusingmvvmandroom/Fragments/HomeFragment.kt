package com.example.notesusingmvvmandroom.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View


import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesusingmvvmandroom.Adapter.MyAdapter
import com.example.notesusingmvvmandroom.R
import com.example.notesusingmvvmandroom.ViewModel.ViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.search.SearchBar

import androidx.appcompat.widget.SearchView



class HomeFragment : Fragment() {
    private lateinit var noteViewModel: ViewModel
    private lateinit var navController: NavController
    private lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.searchMenu->{

                true

            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val fab = view.findViewById<FloatingActionButton>(R.id.addNoteFab)
        adapter = MyAdapter(requireContext(), emptyList())
        recyclerView.layoutManager=LinearLayoutManager(this.context,LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter = adapter
        noteViewModel = ViewModelProvider(this).get(ViewModel::class.java)

        noteViewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            adapter.updateList(notes)
        }
        fab.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_addNoteFragment2)
        }
        adapter.setItemClickListener(object : MyAdapter.onItemClickListener {
            override fun onItemClickListener(position: Int) {
                val bundle = Bundle()
                val noteID=noteViewModel.allNotes.value?.get(position)?.id
                bundle.putInt("id", noteID!!)
                navController.navigate(R.id.action_homeFragment_to_editNoteFragment, bundle)
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_menu, menu)
        val searchItem = menu.findItem(R.id.searchMenu)
        val searchView = searchItem.actionView as? SearchView

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                noteViewModel.searchNote(newText).observe(viewLifecycleOwner){
                    adapter.updateList(it)
                }
                return true
            }
        })
        searchView?.setOnCloseListener {
            noteViewModel.allNotes.observe(viewLifecycleOwner) { notes ->
                adapter.updateList(notes)
            }
            false
        }
    }

}