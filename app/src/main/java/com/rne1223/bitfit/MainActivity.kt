package com.rne1223.bitfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private val foods = mutableListOf<Food>()
    private lateinit var foodRecyclerView: RecyclerView
    private lateinit var newFoodBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        foodRecyclerView = findViewById<RecyclerView>(R.id.foodRv)

        foodRecyclerView.layoutManager = LinearLayoutManager(this).also {

            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            foodRecyclerView.addItemDecoration(dividerItemDecoration)
        }

       val foodAdapter = FoodAdapter(this, foods)
       foodRecyclerView.adapter = foodAdapter

        lifecycleScope.launch {
            (application as BitFitApplication).db.EventDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    Food(
                       entity.foodName,
                       entity.calories + " Calories",
                    )
                }.also { mappedList ->
                    //Log.v(TAG, mappedList.toString())
                    foods.clear()
                    foods.addAll(mappedList)
                    foodAdapter.notifyDataSetChanged()
                }
            }
        }

        newFoodBtn = findViewById(R.id.btn_newFood)
        newFoodBtn.setOnClickListener{
            val context = it.context
            val intent = Intent(context, AddFoodActivity::class.java)
            context.startActivity(intent)
        }
    }
}