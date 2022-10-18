package com.rne1223.bitfit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlin.Int.Companion.MAX_VALUE

private const val TAG = "ChartFragment"

class ChartFragment : Fragment() {
    private lateinit var tvAvg: TextView
    private lateinit var tvMin: TextView
    private lateinit var tvMax: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chart, container, false)

        tvAvg = view.findViewById<TextView>(R.id.chart_avg)
        tvMin = view.findViewById<TextView>(R.id.chart_min)
        tvMax = view.findViewById<TextView>(R.id.chart_max)

        var max = Int.MIN_VALUE
        var min = Int.MAX_VALUE
        var avg = 0

        lifecycleScope.launch {
            (activity?.application as BitFitApplication).db.EventDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                   entity.calories
                }.also { mappedList ->
                    for (i in mappedList) {

                        if (min > i.toInt()) {
                            min = i.toInt()
                        }

                        if(max < i.toInt()){
                            max = i.toInt()
                        }

                        avg += i.toInt()
                    }
                    avg /= mappedList.size

                    Log.v(TAG, mappedList.toString())
                    Log.v(TAG, "${min.toString()} ${max.toString()} ")

                    tvAvg.text = "Avg: ${avg.toString()}"
                    tvMin.text = "Min: ${min.toString()}"
                    tvMax.text = "Max: ${max.toString()}"
                }
            }
        }

        return view
    }

    companion object {
        fun newInstance() : ChartFragment {
            return ChartFragment()
        }
    }
}