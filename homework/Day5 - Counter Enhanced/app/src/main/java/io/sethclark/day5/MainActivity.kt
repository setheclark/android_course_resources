package io.sethclark.day5

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

private const val BUNDLE_KEY_COUNT = "count"

class MainActivity : AppCompatActivity() {

    lateinit var countTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the layout that will be drawn for this Activity
        setContentView(R.layout.activity_main)

        // Assign the views from the layout to class and method scoped variables
        countTextView = findViewById(R.id.textview_count)
        val incrementButton = findViewById<Button>(R.id.button_increment)
        val decrementButton = findViewById<Button>(R.id.button_decrement)


        if (savedInstanceState == null) {
            // This is the first time the screen has been loaded.  Set counter to '0'
            countTextView.text = 0.toString()
        } else {
            // Pull the stored counter out of saveInstanceState that was stored in onSaveInstanceState
            countTextView.text = savedInstanceState.getString(BUNDLE_KEY_COUNT)
        }

        countTextView.setOnClickListener { countTextView.text = 0.toString() }

        // Increment button onClick
        incrementButton.setOnClickListener {
            countTextView.assignWithCurrentInt { curr -> curr + 1 }
        }

        // Increment button onLongClick
        incrementButton.setOnLongClickListener {
            countTextView.assignWithCurrentInt { curr -> curr + 5 }
            false
        }

        // Decrement button onClick
        decrementButton.setOnClickListener {
            countTextView.assignWithCurrentInt { curr -> curr - 1 }
        }

        // Decrement button onLongClick
        decrementButton.setOnLongClickListener {
            countTextView.assignWithCurrentInt { curr -> curr - 5 }
            false
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        // Store the current count so we can set it when the Activity is re-created
        outState?.putString(BUNDLE_KEY_COUNT, countTextView.text.toString())
    }

    private fun TextView.textAsInt() = text.toString().toInt()

    private inline fun TextView.assignWithCurrentInt(block: (Int) -> Int) {
        text = block(textAsInt()).toString()
    }

}