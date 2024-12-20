package com.example.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val allDogs = mutableListOf<Dog>()
    private lateinit var adapter: DogAdapter
    private lateinit var dogNameEditText: EditText
    private lateinit var addButton: Button
    private lateinit var searchButton: Button
    private lateinit var errorTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicjalizacja widoków
        dogNameEditText = findViewById(R.id.dogNameEditText)
        addButton = findViewById(R.id.addButton)
        searchButton = findViewById(R.id.searchButton)
        errorTextView = findViewById(R.id.errorTextView)
        val recyclerView: RecyclerView = findViewById(R.id.dogsRecyclerView)

        // Konfiguracja RecyclerView
        adapter = DogAdapter(
            allDogs,
            onFavoriteClick = { dog ->
                dog.isFavorite = !dog.isFavorite
                adapter.notifyDataSetChanged()
            },
            onDeleteClick = { dog ->
                allDogs.remove(dog)
                adapter.notifyDataSetChanged()
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Obsługa wpisywania tekstu
        dogNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = s.toString()
                addButton.isEnabled = text.isNotBlank()
                searchButton.isEnabled = text.isNotBlank()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Obsługa kliknięcia przycisku "Dodaj"
        addButton.setOnClickListener {
            val name = dogNameEditText.text.toString()
            if (allDogs.any { it.name == name }) {
                showError("Pies o takiej nazwie już istnieje!")
            } else {
                allDogs.add(Dog(name))
                adapter.notifyDataSetChanged()
                dogNameEditText.text.clear()
                hideError()
            }
        }

        // Obsługa kliknięcia przycisku "Szukaj"
        searchButton.setOnClickListener {
            val name = dogNameEditText.text.toString()
            val filteredDogs = allDogs.filter { it.name.contains(name, ignoreCase = true) }
            adapter = DogAdapter(
                filteredDogs,
                onFavoriteClick = { dog ->
                    dog.isFavorite = !dog.isFavorite
                    adapter.notifyDataSetChanged()
                },
                onDeleteClick = { dog ->
                    allDogs.remove(dog)
                    adapter.notifyDataSetChanged()
                }
            )
            recyclerView.adapter = adapter
        }
    }

    private fun showError(message: String) {
        errorTextView.text = message
        errorTextView.visibility = TextView.VISIBLE
    }

    private fun hideError() {
        errorTextView.visibility = TextView.GONE
    }
}
