package com.example.recyclerviewhometask.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewhometask.Data
import com.example.recyclerviewhometask.ItemOperations
import com.example.recyclerviewhometask.R
import com.example.recyclerviewhometask.SortBy
import com.example.recyclerviewhometask.dragdrop.ItemTouchDelegate
import com.example.recyclerviewhometask.model.Item
import java.util.*

class MyAdapter(
    private val touchDelegateInterface: ItemTouchDelegate,
    private val addLambda: (Item.Currency) -> Unit,
    private val scrollLambda: () -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemOperations {

    private val currenciesAndButton: MutableList<Item> = LinkedList()

    private fun onItemDrag(currencyViewHolder: CurrencyViewHolder) {
        currencyViewHolder.itemView.findViewById<ImageView>(R.id.flag_IV)
            .setOnTouchListener { _, motionEvent ->
                if (motionEvent.actionMasked == MotionEvent.ACTION_DOWN) {
                    touchDelegateInterface.startDragging(currencyViewHolder)
                }
                return@setOnTouchListener true
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.add_button -> {
                val view = inflater.inflate(R.layout.add_button, parent, false)
                ButtonViewHolder(view, addLambda, scrollLambda)
            }
            else -> {
                val currencyViewHolder =
                    CurrencyViewHolder(inflater.inflate(R.layout.item_currency, parent, false))
                onItemDrag(currencyViewHolder)
                currencyViewHolder
            }
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (currenciesAndButton[position]) {
            is Item.AddButton -> R.layout.add_button
            else -> R.layout.item_currency
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CurrencyViewHolder -> holder.bind(currenciesAndButton[position] as Item.Currency)
            is ButtonViewHolder -> holder.bind(currenciesAndButton[position] as Item.AddButton)
        }
    }

    override fun getItemCount() = currenciesAndButton.size

    override fun setItems(data: List<Item>) {
        currenciesAndButton.clear()
        currenciesAndButton.addAll(data)
        notifyDataSetChanged()
    }

    override fun add(currency: Item.Currency, index: Int?) {
        when (index) {
            0 -> {
                addCurrency(currency, index)
            }
            1 -> {
                addCurrency(currency, index)
            }
            else -> {
                // add to last position, if sorting type is not selected yet
                currenciesAndButton.add(currenciesAndButton.size - 1, currency)
                notifyItemInserted(currenciesAndButton.size - 1)
            }
        }
    }

    private fun addCurrency(currency: Item.Currency, index: Int) {
        val currencies = mutableListOf<Item.Currency>()
        currenciesAndButton.forEach {
            if (it is Item.Currency) {
                currencies.add(it)
            }
        }
        currencies.add((currency))

        when (index) {
            0 -> {
                currencies.sortBy { it.currency }
            }
            1 -> {
                currencies.sortBy { it.amount }
            }
        }

        val sortedItem = mutableListOf<Item>()
        sortedItem.addAll(0, currencies)
        sortedItem.add(currenciesAndButton.last())

        setItems(sortedItem)
    }

    override fun delete(adapterPosition: Int) {
        currenciesAndButton.removeAt(adapterPosition)
        notifyDataSetChanged()
    }

    override fun sortItems(sortingType: SortBy) {
        val currencies = mutableListOf<Item.Currency>()
        currenciesAndButton.forEach {
            if (it is Item.Currency) {
                currencies.add(it)
            }
        }

        when (sortingType) {
            SortBy.ALPHABET -> currencies.sortBy { it.currency }
            SortBy.COST -> currencies.sortBy { it.amount }
        }

        val sortedCurrenciesAndButton = mutableListOf<Item>()
        sortedCurrenciesAndButton.addAll(0, currencies)
        sortedCurrenciesAndButton.add(currenciesAndButton.last())

        setItems(sortedCurrenciesAndButton)
    }

    override fun resetSorting() {
        val originalItems: List<Item> = Data.elements
        setItems(originalItems)
    }

    fun moveItem(from: Int, to: Int) {
        val fromList = currenciesAndButton[from]
        currenciesAndButton.removeAt(from)
        if (to < from) {
            currenciesAndButton.add(to, fromList)
        } else {
            currenciesAndButton.add(to - 1, fromList)
        }
    }
}