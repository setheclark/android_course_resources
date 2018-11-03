package io.sethclark.day5

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    val countBundleKey = "count"
    lateinit var countTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        countTextView = findViewById(R.id.textview_count)
        val incrementButton = findViewById<Button>(R.id.button_increment)

        if (savedInstanceState == null) {
            countTextView.text = "0"
        } else {
            countTextView.text = savedInstanceState.getString(countBundleKey)
        }




        incrementButton.setOnClickListener {
            val currentValue = countTextView.text.toString().toInt()
            countTextView.text = "${currentValue + 1}"
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(countBundleKey, countTextView.text.toString())
    }
}