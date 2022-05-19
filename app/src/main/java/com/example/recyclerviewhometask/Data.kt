package com.example.recyclerviewhometask

import com.example.recyclerviewhometask.model.Item

object Data {

    private val currency1 = Item.Currency(23450, "USA", R.drawable.usa_flag)
    private val currency2 = Item.Currency(245, "CHN", R.drawable.ic_launcher_background)
    private val currency3 = Item.Currency(100, "KZ", R.drawable.kz_flag)
    private val currency4 = Item.Currency(6450, "TRK", R.drawable.ic_launcher_foreground)
    private val currency5 = Item.Currency(100, "USA", R.drawable.usa_flag)
    private val currency6 = Item.Currency(911, "KZ", R.drawable.kz_flag)
    private val currency7 = Item.Currency(51843, "TRK", R.drawable.ic_launcher_foreground)
    private val currency8 = Item.Currency(9070, "CHN", R.drawable.ic_launcher_background)

    val elements: List<Item> = listOf(
        currency1,
        currency2,
        currency3,
        currency4,
        currency5,
        currency6,
        currency7,
        currency8,
        Item.AddButton("Добавить", R.drawable.plus_image)
    )

}