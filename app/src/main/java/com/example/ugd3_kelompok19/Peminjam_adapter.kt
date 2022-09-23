package com.example.ugd3_kelompok19

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ugd3_kelompok19.room.User
import kotlinx.android.synthetic.main.activity_peminjam_adapter.view.*

class Peminjam_adapter(private val users: ArrayList<User>, private val listener: OnAdapterListener) :
    RecyclerView.Adapter<Peminjam_adapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            NoteViewHolder {
        return NoteViewHolder(

            LayoutInflater.from(parent.context).inflate(R.layout.activity_peminjam_adapter,parent, false)
        )
    }
    override fun onBindViewHolder(holder: NoteViewHolder, position:
    Int) {
        val user = users[position]
        holder.view.text_title.text = user.title
        holder.view.text_title.setOnClickListener{
            listener.onClick(user)
        }
    }
    override fun getItemCount() = users.size
    inner class NoteViewHolder( val view: View) :
        RecyclerView.ViewHolder(view)
    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<User>){
        users.clear()
        users.addAll(list)
        notifyDataSetChanged()
    }
    interface OnAdapterListener {
        fun onClick(user: User)
    }
}