package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DogAdapter(
    private val dogs: List<Dog>,
    private val onFavoriteClick: (Dog) -> Unit,
    private val onDeleteClick: (Dog) -> Unit
) : RecyclerView.Adapter<DogAdapter.DogViewHolder>() {

    inner class DogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dogNameTextView: TextView = itemView.findViewById(R.id.dogNameTextView)
        val favoriteIcon: ImageView = itemView.findViewById(R.id.favoriteIcon)
        val deleteIcon: ImageView = itemView.findViewById(R.id.deleteIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.dog_item, parent, false)
        return DogViewHolder(view)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val dog = dogs[position]
        holder.dogNameTextView.text = dog.name
        holder.favoriteIcon.setImageResource(
            if (dog.isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite_border
        )
        holder.favoriteIcon.setOnClickListener { onFavoriteClick(dog) }
        holder.deleteIcon.setOnClickListener { onDeleteClick(dog) }
    }

    override fun getItemCount(): Int = dogs.size
}
