package com.example.recyclerviewexample

import android.animation.LayoutTransition
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import android.widget.Adapter
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    lateinit var searchView : SearchView
    val data = ArrayList<ItemViewModel>()
    lateinit var adapter : CustomAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)


        for (i in 1..40)
            data.add(ItemViewModel(R.drawable.dot,"Item $i","A RecyclerView is an advanced version of ListView with improved performance. When you have a long list of items to show you can use RecyclerView. " +
                    "It has the ability to reuse its views. In RecyclerView when the View goes out of the screen or not visible to the user it won’t destroy it, it will reuse these views. " +
                    "This feature helps in reducing power consumption and providing more responsiveness to the application."))
        adapter = CustomAdapter(data)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)

        searchView = findViewById(R.id.searchView)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                filterList(p0)
               return true
            }

        })
    }

    private fun filterList(p0: String?) {
        val filtered : ArrayList<ItemViewModel> = ArrayList()
        for (item in data)
        {
            if(item.text.lowercase().contains(p0!!.toLowerCase()))
            {
                filtered.add(item)
            }
        }
        if(filtered.isEmpty())
        {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show()
            adapter.setfilteredList(filtered)
        }
        else{
            adapter.setfilteredList(filtered)
        }
    }

}