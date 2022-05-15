package com.example.recyclerviewhometask.recyclerview

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewhometask.R
import com.example.recyclerviewhometask.dragdrop.ItemTouchDelegate
import com.example.recyclerviewhometask.model.ItemViewHolder
import java.util.*

class MyAdapter(
    private val touchDelegateInterface: ItemTouchDelegate,
    private val addLambda: (ItemViewHolder.Currency) -> Unit,
    private val scrollLambda: () -> Unit,
    private val openFragment: () -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemLists: MutableList<ItemViewHolder> = LinkedList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.add_button -> {
                val view = inflater.inflate(R.layout.add_button, parent, false)
                ButtonViewHolder(view, addLambda, scrollLambda, openFragment)
            }
            else -> {
                val currencyViewHolder =
                    CurrencyViewHolder(inflater.inflate(R.layout.item_currency, parent, false))
                onItemDrag(currencyViewHolder)
                currencyViewHolder
            }
        }
    }

    private fun onItemDrag(currencyViewHolder: CurrencyViewHolder) {
        currencyViewHolder.itemView.findViewById<ImageView>(R.id.flag_IV)
            .setOnTouchListener { _, motionEvent ->
                if (motionEvent.actionMasked == MotionEvent.ACTION_DOWN) {
                    touchDelegateInterface.startDragging(currencyViewHolder)
                }
                return@setOnTouchListener true
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

    fun moveItem(from: Int, to: Int) {
        val fromList = itemLists[from]
        itemLists.removeAt(from)
        if (to < from) {
            itemLists.add(to, fromList)
        } else {
            itemLists.add(to - 1, fromList)
        }
    }

    // helper class to calculate differences between two lists
    class MyDiffUtilClass(var oldList: List<ItemViewHolder>, var newList: List<ItemViewHolder>) :
        DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            // check id
            return false
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}