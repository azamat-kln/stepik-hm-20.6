package com.example.recyclerviewhometask

import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class ButtonViewHolder(view: View, private val toastLambda: (String) -> Unit) :
    RecyclerView.ViewHolder(view) {
    private val addButton: Button = view.findViewById(R.id.button_parent)

    fun showToast() {
        addButton.setOnClickListener {
            toastLambda("Button clicked")
        }
    }

}
