package com.example.recyclerviewhometask.recyclerview

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewhometask.R
import com.example.recyclerviewhometask.model.ItemViewHolder

class ButtonViewHolder(view: View, private val toastLambda: (String) -> Unit) :
    RecyclerView.ViewHolder(view) {

    private val plusRectangle = view.findViewById<View>(R.id.plus_view)
    private val plusImage = view.findViewById<ImageView>(R.id.plus_image_view)
    private val addTextView = view.findViewById<TextView>(R.id.add_text_view)

    fun bind(item: ItemViewHolder.AddButton) {
        addTextView.text = item.text
        plusImage.setImageResource(item.imageRes)
        plusRectangle.setOnClickListener {
            toastLambda("button clicked")
        }
    }

}
