package com.rne1223.bitfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private val foods = mutableListOf<Food>()

    private lateinit var newFoodBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val logsFragment: Fragment = LogsFragment()
        val chartFragment: Fragment = ChartFragment()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // handle navigation selection
        bottomNavigationView.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment

            when (item.itemId) {
                R.id.nav_logs -> fragment = logsFragment
                R.id.nav_chart -> fragment = chartFragment
            }

            replaceFragment(fragment)
            true
        }
        // Set default selection
        bottomNavigationView.selectedItemId = R.id.nav_logs

        newFoodBtn = findViewById(R.id.btn_newFood)
        newFoodBtn.setOnClickListener{
            val context = it.context
            val intent = Intent(context, AddFoodActivity::class.java)
            context.startActivity(intent)
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.logs_frame_layout, fragment)
        fragmentTransaction.commit()
    }

}