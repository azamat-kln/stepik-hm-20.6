package com.example.recyclerviewhometask

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.DialogFragment
import com.example.recyclerviewhometask.model.Item
import com.example.recyclerviewhometask.recyclerview.MyAdapter
import com.google.android.material.snackbar.Snackbar

class MyDialogFragment : DialogFragment(R.layout.custom_dialog) {

    companion object {
        fun newInstance(adapter: MyAdapter, currency: Item.Currency): MyDialogFragment {
            val args = Bundle()
            args.putSerializable("key1", currency)
            args.putSerializable("key2", adapter)
            val fragment = MyDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val item = arguments?.getSerializable("key1") as Item.Currency
        val adapter = arguments?.getSerializable("key2") as MyAdapter

        with(view) {
            findViewById<Button>(R.id.cancel_btn).setOnClickListener {
                dismiss()
            }
            findViewById<Button>(R.id.remove_tn).setOnClickListener {
                adapter.deleteItem(item)
                dismiss()
            }
        }

    }

}