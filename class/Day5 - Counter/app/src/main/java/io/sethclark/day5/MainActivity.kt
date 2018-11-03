package io.sethclark.day5

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    val countBundleKey = "count"
    lateinit var countTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the layout that will be drawn for this Activity
        setContentView(R.layout.activity_main)

        // Assign the views from the layout to class and method scoped variables
        countTextView = findViewById(R.id.textview_count)
        val incrementButton = findViewById<Button>(R.id.button_increment)


        if (savedInstanceState == null) {
            // This is the first time the screen has been loaded.  Set counter to '0'
            countTextView.text = "0"
        } else {
            // Pull the stored counter out of saveInstanceState that was stored in onSaveInstanceState
            countTextView.text = savedInstanceState.getString(countBundleKey)
        }


        // Increment button onClick
        incrementButton.setOnClickListener {
            // Get current value from text view and convert it to an Int
            val currentValue = countTextView.text.toString().toInt()
            // Reassign the text view to the current view plus 1.
            countTextView.text = "${currentValue + 1}"
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        // Store the current count so we can set it when the Activity is re-created
        outState?.putString(countBundleKey, countTextView.text.toString())
    }
}