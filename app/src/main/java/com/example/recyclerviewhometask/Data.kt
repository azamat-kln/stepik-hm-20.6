package com.example.recyclerviewhometask

import com.example.recyclerviewhometask.model.ItemViewHolder

object Data {

    private val currency1 = ItemViewHolder.Currency(23450, "USA", R.drawable.usa_flag)
    private val currency2 = ItemViewHolder.Currency(245, "CHN", R.drawable.ic_launcher_background)
    private val currency3 = ItemViewHolder.Currency(100, "KZ", R.drawable.kz_flag)
    private val currency4 = ItemViewHolder.Currency(6450, "TRK", R.drawable.ic_launcher_foreground)
    private val currency5 = ItemViewHolder.Currency(100, "USA", R.drawable.usa_flag)
    private val currency6 = ItemViewHolder.Currency(911, "KZ", R.drawable.kz_flag)
    private val currency7 = ItemViewHolder.Currency(51843, "TRK", R.drawable.ic_launcher_foreground)
    private val currency8 = ItemViewHolder.Currency(9070, "CHN", R.drawable.ic_launcher_background)

    val itemLists: List<ItemViewHolder> = listOf(
        currency1,
        currency2,
        currency3,
        currency4,
        currency5,
        currency6,
        currency7,
        currency8,
        ItemViewHolder.AddButton("Добавить", R.drawable.plus_image)
    )

}