package com.example.parcial2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter
    private var listRyM = mutableListOf<MyResult>()
    private lateinit var statusSpinner: Spinner
    private lateinit var speciesSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewRyM)
        statusSpinner = findViewById(R.id.statusSpinner)
        speciesSpinner = findViewById(R.id.speciesSpinner)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Adapter(listRyM)
        recyclerView.adapter = adapter

        adapter.onItemClickListener = { myresulT ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("myresult", myresulT)
            startActivity(intent)
        }

        setupSpinners()
        getCharacter(ALL_CHARACTER)
    }

    private fun setupSpinners() {
        val statusOptions = listOf("Todos", "alive", "dead", "unknown")
        val speciesOptions = listOf("Todas", "Human", "Alien", "Robot", "Mythological Creature", "Humanoid")

        statusSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, statusOptions)
        speciesSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, speciesOptions)

        val listener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val status = statusSpinner.selectedItem.toString()
                val species = speciesSpinner.selectedItem.toString()
                val url = buildFilteredUrl(status, species)
                getCharacter(url)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        statusSpinner.onItemSelectedListener = listener
        speciesSpinner.onItemSelectedListener = listener
    }

    private fun buildFilteredUrl(status: String, species: String): String {
        val base = "https://rickandmortyapi.com/api/character/"
        val queryParams = mutableListOf<String>()

        if (status != "Todos") queryParams.add("status=$status")
        if (species != "Todas") queryParams.add("species=$species")

        return if (queryParams.isEmpty()) base else "$base?${queryParams.joinToString("&")}"
    }

    private fun getCharacter(url: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getAllCharacters(url)
            val response = call.body()

            runOnUiThread {
                if (call.isSuccessful) {
                    val character = response?.results
                    listRyM.clear()
                    character?.let { listRyM.addAll(it) }
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
