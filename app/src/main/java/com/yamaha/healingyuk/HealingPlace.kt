package com.yamaha.healingyuk

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HealingPlace(
    val id: String,
    val name: String,
    val shortDescription: String,
    val category: String,
    val imageUrl: String,
    val longDescription: String,
    var isFavorite: Boolean = false
) : Parcelable
