package com.example.recyclerviewhometask

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity

class MyDialogFragment : DialogFragment(R.layout.custom_dialog) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(view) {
            findViewById<Button>(R.id.cancel_btn).setOnClickListener {
                dismiss()
            }

            findViewById<Button>(R.id.remove_tn).setOnClickListener {
                val dialogCallBack: DialogCallBack = activity as DialogCallBack
                dialogCallBack.onRemoveButtonClicked()
                dialogCallBack.showSnackBar()
            }

        }
    }

}

interface DialogCallBack {
    fun onRemoveButtonClicked()
    fun showSnackBar()
}