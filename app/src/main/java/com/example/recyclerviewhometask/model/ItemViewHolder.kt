package com.example.recyclerviewhometask.model

import androidx.annotation.DrawableRes

sealed class ItemViewHolder {

    data class Currency(
        val amount: Int = 0,
        val currency: String = "",
        @DrawableRes val flagDrawableRes: Int = 0
    ) : ItemViewHolder()

    data class AddButton(val text: String, @DrawableRes val imageRes: Int) : ItemViewHolder()
}