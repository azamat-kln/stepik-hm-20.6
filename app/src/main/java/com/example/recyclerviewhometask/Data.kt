package com.example.recyclerviewhometask

import com.example.recyclerviewhometask.model.AddButton
import com.example.recyclerviewhometask.model.Currency
import com.example.recyclerviewhometask.model.ItemViewHolder

object Data {

    private val currency1 = Currency(23450, "USA", R.drawable.usa_flag)
    private val currency2 = Currency(245, "CHN", R.drawable.ic_launcher_background)
    private val currency3 = Currency(100, "KZ", R.drawable.image_1)
    private val currency4 = Currency(6450, "TRK", R.drawable.ic_launcher_foreground)
    private val currency5 = Currency(100, "USA", R.drawable.usa_flag)
    private val currency6 = Currency(911, "KZ", R.drawable.image_1)
    private val currency7 = Currency(51843, "TRK", R.drawable.ic_launcher_foreground)
    private val currency8 = Currency(9070, "CHN", R.drawable.ic_launcher_background)

    val itemLists: List<ItemViewHolder> = listOf(
        currency1,
        currency2,
        currency3,
        currency4,
        currency5,
        currency6,
        currency7,
        currency8,
        AddButton()
    )

}