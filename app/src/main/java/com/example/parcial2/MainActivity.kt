package com.example.parcial2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var title: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter
    private var listRyM = mutableListOf<MyResult>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewRyM)
        title = findViewById(R.id.textViewTitle)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Adapter(listRyM)
        recyclerView.adapter = adapter

        getCharacter()

    }


    private fun getCharacter() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getAllCharacters(ALL_CHARACTER)
            val response = call.body()

            runOnUiThread {
                if (call.isSuccessful) {
                    val character = response?.results
                    character?.forEach{
                        listRyM.add(it)
                    }
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
        const val ALL_CHARACTER = "https://rickandmortyapi.com/api/character"
    }
}
