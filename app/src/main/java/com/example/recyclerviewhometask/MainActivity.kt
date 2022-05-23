package com.example.recyclerviewhometask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.*
import com.example.recyclerviewhometask.dragdrop.ItemTouchDelegate
import com.example.recyclerviewhometask.dragdrop.DragDropHelper
import com.example.recyclerviewhometask.model.Item
import com.example.recyclerviewhometask.recyclerview.MyAdapter
import com.example.recyclerviewhometask.swipe.LeftSwiped
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), ItemTouchDelegate {

    private lateinit var myRecyclerView: RecyclerView
    private lateinit var adapterItems: MyAdapter
    private lateinit var myLayoutManager: LinearLayoutManager
    private lateinit var customToolbar: Toolbar
    private var snapPosition = RecyclerView.NO_POSITION
    private var dragDropHelper: ItemTouchHelper? = null
    private var chosenIndex: Int? = null
    private lateinit var itemToDelete: Item.Currency

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        customToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(customToolbar)
        setupAdapter()
    }

    private fun setupAdapter() {
        myLayoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        adapterItems = MyAdapter(this, addCurrencyHof(), scrollToEndHof(), changeToolBarState())
        myRecyclerView = findViewById<RecyclerView?>(R.id.recycler_view).apply {
            layoutManager = myLayoutManager
            adapter = adapterItems
        }
        adapterItems.setItems(Data.elements)

        setupSwipeAndDragDrop()
        setupSnapping(myLayoutManager)
    }

    private fun addCurrencyHof(): (Item.Currency) -> Unit {
        val addCurrency: (Item.Currency) -> Unit =
            { currency: Item.Currency ->
                adapterItems.add(currency, chosenIndex)
            }
        return addCurrency
    }

    private fun scrollToEndHof(): () -> Unit {
        val scrollToEnd: () -> Unit = {
            val smoothScroller = object : LinearSmoothScroller(this) {
                override fun getVerticalSnapPreference(): Int = SNAP_TO_START
            }
            smoothScroller.targetPosition = adapterItems.itemCount
            myLayoutManager.startSmoothScroll(smoothScroller)
        }
        return scrollToEnd
    }

    private fun changeToolBarState(): (Item.Currency) -> Unit {
        val changedToolbar: (Item.Currency) -> Unit = {
            customToolbar.title = "item selected"
            customToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.grey_background))
            customToolbar.menu.findItem(R.id.delete_item).isVisible = true
            customToolbar.menu.findItem(R.id.sort_by).isVisible = false
            customToolbar.menu.findItem(R.id.reset_sorting).isVisible = false

            itemToDelete = it
            Log.i("MainActivity", "item: ${itemToDelete.amount} ${itemToDelete.currency}")
        }
        return changedToolbar
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
                adapterItems.sortItemsBy(SortBy.ALPHABET)
                chosenIndex = 0
                true
            }
            R.id.sort_by_cost -> {
                adapterItems.sortItemsBy(SortBy.COST)
                chosenIndex = 1
                true
            }
            R.id.reset_sorting -> {
                adapterItems.resetSorting()
                true
            }
            R.id.delete_item -> {
                MyDialogFragment.newInstance(adapterItems, itemToDelete)
                    .show(supportFragmentManager, null)
                setDefaultToolbarState()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun setDefaultToolbarState() {
        customToolbar.apply {
            title = "Converter"
            setBackgroundColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.white_toolbar_background
                )
            )
            menu.findItem(R.id.delete_item).isVisible = false
            menu.findItem(R.id.sort_by).isVisible = true
            menu.findItem(R.id.reset_sorting).isVisible = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_currency, menu)
        return true
    }
}