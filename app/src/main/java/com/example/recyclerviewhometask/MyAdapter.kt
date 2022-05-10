package com.example.recyclerviewhometask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val currencyLists: List<Currency>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val currencyTV: TextView = view.findViewById(R.id.currency_tv)
        val flagIV: ImageView = view.findViewById(R.id.flag_IV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_currency, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.currencyTV.text = currencyLists[position].currency
        holder.flagIV.setImageResource(currencyLists[position].flagDrawableRes)
    }

    override fun getItemCount() = currencyLists.size

}