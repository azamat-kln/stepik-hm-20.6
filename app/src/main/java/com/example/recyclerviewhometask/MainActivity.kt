package com.example.recyclerviewhometask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.*
import com.example.recyclerviewhometask.dragdrop.ItemTouchDelegate
import com.example.recyclerviewhometask.dragdrop.MyDragDrop
import com.example.recyclerviewhometask.model.ItemViewHolder
import com.example.recyclerviewhometask.recyclerview.MyAdapter
import com.example.recyclerviewhometask.swipe.LeftSwiped

class MainActivity : AppCompatActivity(), ItemTouchDelegate {

    private var myRecyclerView: RecyclerView? = null
    private var adapterItems: MyAdapter? = null
    private var snapPosition = RecyclerView.NO_POSITION
    private var dragDropHelper: ItemTouchHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupAdapter()
    }

    private fun setupAdapter() {
        val addCurrencyLambda: (ItemViewHolder.Currency) -> Unit = { currency: ItemViewHolder.Currency ->
            adapterItems?.addCurrency(currency)
        }

        val openFragment: () -> Unit = {
            Toast.makeText(this, "btn clicked", Toast.LENGTH_SHORT).show()
        }

        val myLayoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)

        val scrollLambda: () -> Unit = {
            val smoothScroller = object : LinearSmoothScroller(this) {
                override fun getVerticalSnapPreference(): Int = LinearSmoothScroller.SNAP_TO_START
            }
            smoothScroller.targetPosition = adapterItems?.itemCount ?: 0
            myLayoutManager.startSmoothScroll(smoothScroller)
        }

        adapterItems = MyAdapter(this, addCurrencyLambda, scrollLambda, openFragment)
        myRecyclerView = findViewById(R.id.recycler_view)

        myRecyclerView?.apply {
            layoutManager = myLayoutManager
            adapter = adapterItems
        }

        // set left swipe
        ItemTouchHelper(LeftSwiped(adapterItems)).attachToRecyclerView(myRecyclerView)

        // set drag and drop with MyDragDrop class
        dragDropHelper = ItemTouchHelper(MyDragDrop())
        dragDropHelper?.attachToRecyclerView(myRecyclerView)

        adapterItems?.setItems(Data.itemLists)
        setupSnapping(myLayoutManager)
    }

    private fun setupSnapping(myLayoutManager: LinearLayoutManager) {
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(myRecyclerView)
        myRecyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                snapHelper.findSnapView(myLayoutManager)?.let {
                    val position = myLayoutManager.getPosition(it)
                    if (snapPosition != position) {
                        snapPosition = position
                        // invoke some callback
                    }
                }

            }
        })
    }

    override fun startDragging(viewHolder: RecyclerView.ViewHolder) {
        dragDropHelper?.startDrag(viewHolder)
    }

}