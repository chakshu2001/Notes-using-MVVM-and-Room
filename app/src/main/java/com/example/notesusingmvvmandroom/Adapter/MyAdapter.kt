package com.example.notesusingmvvmandroom.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.notesusingmvvmandroom.Model.Note
import com.example.notesusingmvvmandroom.R


class MyAdapter(val context:Context,private var data:List<Note>):RecyclerView.Adapter<MyAdapter.viewHolder>() {
    private lateinit var myListener:onItemClickListener
    interface onItemClickListener{
        fun onItemClickListener(position:Int)
    }
  //  private lateinit var navController: NavController
    fun setItemClickListener(listener:onItemClickListener){
        myListener=listener
    }
    class viewHolder(itemView: View,listener: onItemClickListener):RecyclerView.ViewHolder(itemView){
        val title=itemView.findViewById<TextView>(R.id.title)
        val content=itemView.findViewById<TextView>(R.id.content)

        init {
            itemView.setOnClickListener{
                listener.onItemClickListener(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val itemView=LayoutInflater.from(context)
        val view=itemView.inflate(R.layout.note_layout,parent,false)

        return viewHolder(view,myListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }
    fun anim(view:View){
        var animation= AlphaAnimation(0f, 1f);

        animation.duration=1500
        view.startAnimation(animation)
    }
    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentNote=data[position]
        //navController= Navigation.findNavController(holder.itemView)
        holder.title.text=currentNote.title
        holder.content.text=currentNote.notes
        anim(holder.itemView)

    }
    fun updateList(newList:List<Note>){
        this.data=newList
        notifyDataSetChanged()

    }
}