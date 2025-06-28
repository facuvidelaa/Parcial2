package com.example.parcial2

import android.content.DialogInterface.OnClickListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter (private val rym : List<MyResult>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    lateinit var onItemClickListener: (MyResult) -> Unit

    inner class ViewHolder (val view : View) : RecyclerView.ViewHolder(view) {
        private val tvMag: TextView = view.findViewById(R.id.textViewMag)
        private val tvPlace: TextView = view.findViewById(R.id.textViewPlace)

        fun bind(result: MyResult) {
            tvMag.text = result.name
            tvPlace.text = result.species

            view.setOnClickListener {
                onItemClickListener(result)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return rym.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quake = rym[position]
        holder.bind(quake)
    }
}
