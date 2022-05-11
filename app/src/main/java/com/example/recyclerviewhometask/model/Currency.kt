package com.example.recyclerviewhometask.model

import androidx.annotation.DrawableRes

data class Currency(
    val amount: Int = 0,
    val currency: String = "",
    @DrawableRes val flagDrawableRes: Int = 0
): ItemViewHolder
