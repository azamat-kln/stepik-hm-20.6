package com.example.recyclerviewhometask.recyclerview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewhometask.R
import com.example.recyclerviewhometask.model.Item

class ButtonViewHolder(
    view: View,
    private val addCurrency: (Item.Currency) -> Unit,
    private val scrollToEnd: () -> Unit
) :
    RecyclerView.ViewHolder(view) {

    private val plusImage = view.findViewById<ImageView>(R.id.plus_image_view)
    private val addTextView = view.findViewById<TextView>(R.id.add_text_view)

    fun bind(addButton: Item.AddButton) {
        addTextView.text = addButton.text
        plusImage.setImageResource(addButton.imageRes)

        addTextView.setOnClickListener {
            val currencyConst = Item.Currency(400, "KZ", R.drawable.kz_flag)
            addCurrency(currencyConst)
            scrollToEnd()
            // openFragment()
        }
    }

}
