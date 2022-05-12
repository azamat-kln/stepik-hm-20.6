package com.example.recyclerviewhometask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewhometask.recyclerview.MyAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupAdapter()
    }

    private fun setupAdapter() {
        val toastLambda =
            { currency: String -> Toast.makeText(this, currency, Toast.LENGTH_SHORT).show() }

        val currencyAdapter = MyAdapter(Data.itemLists, toastLambda)
        findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = currencyAdapter
        }
    }

}