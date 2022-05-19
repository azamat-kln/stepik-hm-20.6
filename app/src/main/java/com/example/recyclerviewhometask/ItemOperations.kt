package com.example.recyclerviewhometask

import com.example.recyclerviewhometask.model.Item

interface ItemOperations {

    fun setItems(data: List<Item>)

    fun add(currency: Item.Currency, index: Int?)

    fun delete(adapterPosition: Int)

    fun sortItems(sortingType: SortBy)

    fun resetSorting()
}