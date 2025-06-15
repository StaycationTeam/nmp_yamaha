package com.yamaha.healingyuk.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yamaha.healingyuk.databinding.ItemHealingPlaceBinding
import com.yamaha.healingyuk.HealingPlace

class   HealingAdapter(
    private val items: List<HealingPlace>,
    private val onReadMoreClicked: (HealingPlace) -> Unit
) : RecyclerView.Adapter<HealingAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemHealingPlaceBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHealingPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = items[position]
        holder.binding.apply {
            tvName.text = place.name
            tvCategory.text = place.category
            tvShortDesc.text = place.shortDescription

            Picasso.get()
                .load(place.imageUrl)
                .into(ivPhoto)

            btnReadMore.setOnClickListener {
                onReadMoreClicked(place)
            }
        }
    }
}