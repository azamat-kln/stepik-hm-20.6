package com.example.recyclerviewhometask

import com.example.recyclerviewhometask.model.Item

interface ItemOperations {

    fun setItems(data: List<Item>)

    fun add(currency: Item.Currency, index: Int?)

    fun deleteItemAt(position: Int)

    fun deleteItem(currency: Item.Currency)

    fun sortCurrencies(currencies: List<Item.Currency>, sortingType: SortBy)

    fun resetSorting()
}