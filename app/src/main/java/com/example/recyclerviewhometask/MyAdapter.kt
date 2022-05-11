package com.example.recyclerviewhometask

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewhometask.model.AddButton
import com.example.recyclerviewhometask.model.Currency
import com.example.recyclerviewhometask.model.ItemViewHolder

class MyAdapter(
    private val currencyLists: List<ItemViewHolder>,
    private val toastLambda: (String) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.add_button -> {
                val view = inflater.inflate(R.layout.add_button, parent, false)
                ButtonViewHolder(view, toastLambda)
            }
            else -> CurrencyViewHolder(
                inflater.inflate(R.layout.item_currency, parent, false),
                toastLambda
            )
        }
    }

    override fun getItemViewType(position: Int): Int =
        when (currencyLists[position]) {
            is AddButton -> R.layout.add_button
            else -> R.layout.item_currency
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CurrencyViewHolder -> holder.bind(currencyLists[position] as Currency)
            is ButtonViewHolder -> holder.showToast()
        }
    }

    override fun getItemCount() = currencyLists.size
}