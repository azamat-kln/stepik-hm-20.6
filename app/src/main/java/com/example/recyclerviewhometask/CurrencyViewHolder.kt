package com.example.recyclerviewhometask

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewhometask.model.Currency
import com.google.android.material.textfield.TextInputLayout

class CurrencyViewHolder(view: View, private val toastLambda: (String) -> Unit) :
    RecyclerView.ViewHolder(view) {
    private val currencyTV: TextView = view.findViewById(R.id.currency_tv)
    private val flagIV: ImageView = view.findViewById(R.id.flag_IV)
    private val inputCurrency: TextInputLayout = view.findViewById(R.id.text_input_currency)

    fun bind(currency: Currency) {
        currencyTV.text = currency.currency
        flagIV.setImageResource(currency.flagDrawableRes)
        inputCurrency.editText?.setText(currency.amount.toString())
        flagIV.setOnClickListener {
            toastLambda(currency.currency)
        }
    }

}