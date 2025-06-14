package com.yamaha.healingyuk.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yamaha.healingyuk.HealingPlace
import com.yamaha.healingyuk.databinding.ItemFavouritePlaceBinding

class FavoritesAdapter(
    private val items: List<HealingPlace>
) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemFavouritePlaceBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFavouritePlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = items[position]
        holder.binding.apply {
            tvName.text = place.name
            tvCategory.text = place.category

            Picasso.get()
                .load(place.imageUrl)
                .into(ivPhoto)
        }
    }
}
