package com.rne1223.bitfit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

private const val TAG = "LogsFragment"

class LogsFragment() : Fragment() {

    private val foods = mutableListOf<Food>()
    private lateinit var logsRecyclerView: RecyclerView
    private lateinit var logsAdapter: FoodAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Change this statement to store the view in a variable instead of a return statement
        val view = inflater.inflate(R.layout.fragment_logs, container, false)

        // Add these configurations for the recyclerView and to configure the adapter
        val layoutManager = LinearLayoutManager(context)
        logsRecyclerView = view.findViewById(R.id.logs_recycler_view)
        logsRecyclerView.layoutManager = layoutManager
        logsRecyclerView.setHasFixedSize(true)
        logsAdapter = FoodAdapter(view.context, foods)
        logsRecyclerView.adapter = logsAdapter

        lifecycleScope.launch {
            (activity?.application as BitFitApplication).db.EventDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    Food(
                        entity.foodName,
                        entity.calories + " Calories",
                    )
                }.also { mappedList ->
                    //Log.v(TAG, mappedList.toString())
                    foods.clear()
                    foods.addAll(mappedList)
                    logsAdapter.notifyDataSetChanged()
                }
            }
        }
        // Update the return statement to return the inflated view from above
        return view
    }

    companion object {
        fun newInstance(): LogsFragment{
            return LogsFragment()
        }
    }
}