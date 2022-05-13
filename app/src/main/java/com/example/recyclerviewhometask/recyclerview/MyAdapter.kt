package com.example.recyclerviewhometask.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewhometask.R
import com.example.recyclerviewhometask.model.ItemViewHolder
import java.util.*

class MyAdapter(
    private val addLambda: (ItemViewHolder.Currency) -> Unit,
    private val scrollLambda: () -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemLists: MutableList<ItemViewHolder> = LinkedList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.add_button -> {
                val view = inflater.inflate(R.layout.add_button, parent, false)
                ButtonViewHolder(view, addLambda, scrollLambda)
            }
            else -> CurrencyViewHolder(
                inflater.inflate(R.layout.item_currency, parent, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (itemLists[position]) {
            is ItemViewHolder.AddButton -> R.layout.add_button
            else -> R.layout.item_currency
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CurrencyViewHolder -> holder.bind(itemLists[position] as ItemViewHolder.Currency)
            is ButtonViewHolder -> holder.bind(itemLists[position] as ItemViewHolder.AddButton)
        }
    }

    override fun getItemCount() = itemLists.size

    fun addCurrency(currency: ItemViewHolder.Currency) {
        itemLists.add(itemLists.size - 1, currency)
        notifyItemInserted(itemLists.size - 1)
    }

    fun setItems(data: List<ItemViewHolder>) {
        itemLists.clear()
        itemLists.addAll(data)
        notifyDataSetChanged()
    }

    fun deleteItem(adapterPosition: Int) {
        itemLists.removeAt(adapterPosition)
        notifyDataSetChanged()
    }
}