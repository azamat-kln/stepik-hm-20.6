package com.example.recyclerviewhometask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.*
import com.example.recyclerviewhometask.dragdrop.ItemTouchDelegate
import com.example.recyclerviewhometask.dragdrop.DragDropHelper
import com.example.recyclerviewhometask.model.Item
import com.example.recyclerviewhometask.recyclerview.MyAdapter
import com.example.recyclerviewhometask.swipe.LeftSwiped

class MainActivity : AppCompatActivity(), ItemTouchDelegate {

    private var myRecyclerView: RecyclerView? = null
    private var adapterItems: MyAdapter? = null
    private var snapPosition = RecyclerView.NO_POSITION
    private var dragDropHelper: ItemTouchHelper? = null
    private var chosenIndex: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
        setupAdapter()
    }

    private fun setupAdapter() {
        val addCurrency: (Item.Currency) -> Unit =
            { currency: Item.Currency ->
                adapterItems?.add(currency, chosenIndex)
            }

        val myLayoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)

        val scrollToEnd: () -> Unit = {
            val smoothScroller = object : LinearSmoothScroller(this) {
                override fun getVerticalSnapPreference(): Int = LinearSmoothScroller.SNAP_TO_START
            }
            smoothScroller.targetPosition = adapterItems?.itemCount ?: 0
            myLayoutManager.startSmoothScroll(smoothScroller)
        }

        adapterItems = MyAdapter(this, addCurrency, scrollToEnd)
        myRecyclerView = findViewById(R.id.recycler_view)

        myRecyclerView?.apply {
            layoutManager = myLayoutManager
            adapter = adapterItems
        }

        setupSwipeAndDragDrop()

        adapterItems?.setItems(Data.elements)
        setupSnapping(myLayoutManager)
    }

    private fun setupSwipeAndDragDrop() {
        // set left swipe
        ItemTouchHelper(LeftSwiped(adapterItems)).attachToRecyclerView(myRecyclerView)

        // set drag and drop with MyDragDrop class
        dragDropHelper = ItemTouchHelper(DragDropHelper())
        dragDropHelper?.attachToRecyclerView(myRecyclerView)
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

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val chosenItemId = when (chosenIndex) {
            0 -> R.id.sort_by_alphabet
            1 -> R.id.sort_by_cost
            else -> 0
        }
        menu?.findItem(chosenItemId)?.isChecked = true
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sort_by_alphabet -> {
                adapterItems?.sortItems(SortBy.ALPHABET)
                chosenIndex = 0
                true
            }
            R.id.sort_by_cost -> {
                adapterItems?.sortItems(SortBy.COST)
                chosenIndex = 1
                true
            }
            R.id.reset_sorting -> {
                adapterItems?.resetSorting()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_currency, menu)
        return true
    }
}