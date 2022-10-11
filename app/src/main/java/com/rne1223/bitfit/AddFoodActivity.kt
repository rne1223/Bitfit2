package com.rne1223.bitfit

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

private const val TAG = "AddFoodActivity"

class AddFoodActivity: AppCompatActivity() {

    private lateinit var foodTx: TextView
    private lateinit var caloriesTx: TextView
    private lateinit var addFoodBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addfood_activity)


        addFoodBtn = findViewById<Button>(R.id.btn_Add)

        addFoodBtn.setOnClickListener{

            foodTx = findViewById<EditText>(R.id.enterFoodTx)
            caloriesTx = findViewById<EditText>(R.id.enterCaloriesTx)

            Log.v(TAG, foodTx.text.toString())

            lifecycleScope.launch(IO){
                (application as BitFitApplication).db.EventDao().insertEvent(
                    Event(foodName = foodTx.text.toString(), calories = caloriesTx.text.toString())
                )
            }

            finish()
        }
    }
}