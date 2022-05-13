package com.example.recyclerviewhometask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewhometask.model.ItemViewHolder
import com.example.recyclerviewhometask.recyclerview.MyAdapter

class MainActivity : AppCompatActivity() {

    private var adapterItems: MyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupAdapter()
    }

    private fun setupAdapter() {
        val addBtnLambda: (ItemViewHolder.Currency) -> Unit = { currency: ItemViewHolder.Currency ->
            adapterItems?.addCurrency(currency)
        }

        val myLayoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)

        val scrollLambda = {
            val smoothScroller = object : LinearSmoothScroller(this) {
                override fun getVerticalSnapPreference(): Int = LinearSmoothScroller.SNAP_TO_START
            }
            smoothScroller.targetPosition = adapterItems?.itemCount ?: 0
            myLayoutManager.startSmoothScroll(smoothScroller)
        }

        adapterItems = MyAdapter(addBtnLambda, scrollLambda)
        val myRecyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        myRecyclerView.apply {
            layoutManager = myLayoutManager
            adapter = adapterItems
        }

        ItemTouchHelper(returnItemTouch()).attachToRecyclerView(myRecyclerView)

        adapterItems?.setItems(Data.itemLists)
    }

    private fun returnItemTouch(): ItemTouchHelper.SimpleCallback {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapterItems?.deleteItem(viewHolder.adapterPosition)
            }
        }
        return itemTouchHelperCallback
    }

}