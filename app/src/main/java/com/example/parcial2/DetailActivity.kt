package com.example.parcial2

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private lateinit var avatar : ImageView
    private lateinit var nombre : TextView
    private lateinit var especie : TextView
    private lateinit var location : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val character = intent.getParcelableExtra<MyResult>("myresult")


        nombre = findViewById(R.id.nombreTextView)
        especie = findViewById(R.id.especieTextView)
        location  = findViewById(R.id.locationTextView)
        avatar  = findViewById(R.id.AvatarImageView)

                nombre.text = character?.name
                especie.text = "Especie: ${character!!.species}"
                location.text = "Ubicación: ${character.location.name.toString()}"


        Glide.with(this)
            .load(character.image)
            .into(avatar)
            }
    }

