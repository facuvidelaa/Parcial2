package com.example.parcial2


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide



class Adapter (private val rym : List<MyResult>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    lateinit var onItemClickListener: (MyResult) -> Unit

    inner class ViewHolder (val view : View) : RecyclerView.ViewHolder(view) {
        private val nombre: TextView = view.findViewById(R.id.textViewName)
        private val avatar: ImageView = view.findViewById((R.id.imageViewAvatar))


        fun bind(result: MyResult) {
            nombre.text = result.name

            Glide.with(view)
                .load(result.image)
                .into(avatar)



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
        val character = rym[position]
        holder.bind(character)
    }
}
