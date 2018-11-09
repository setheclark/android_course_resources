package io.sethclark.day7

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {

    // A field to hold a reference to our counter text view
    private lateinit var countTextView: TextView

    // A field to hold a reference to our view model
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set the layout that will be drawn for this Activity
        setContentView(R.layout.activity_main)

        // Here we use ViewModelProviders to get a reference to an instance of
        // MainViewModel.  ViewModelProviders will create and cache the created
        // instance so that subsequent requests for MainViewModel will return the
        // same instance.
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        // Here we're setting up an Observer to observe changes to the counter
        // held by MainViewModel.  Anytime the value of counter in changed in the
        // MainViewModel our Observer will be called with the new value.
        viewModel.counter.observe(this, Observer { counter ->
            // When our Observer is called with the updated count value we will update our
            // UI to show the updated value
            countTextView.text = counter.toString()
        })

        // Assign the views from the layout to class and method scoped variables
        countTextView = findViewById(R.id.textview_count)
        val incrementButton = findViewById<Button>(R.id.button_increment)
        val decrementButton = findViewById<Button>(R.id.button_decrement)

        // Reset the count when the counter view is clicked
        countTextView.setOnClickListener {
            viewModel.resetCounter()
        }

        // Increment button onClick
        incrementButton.setOnClickListener {
            viewModel.incrementCounter()
        }

        // Increment button onLongClick
        incrementButton.setOnLongClickListener {
            viewModel.incrementCounterBy5()
            false
        }

        // Decrement button onClick
        decrementButton.setOnClickListener {
            viewModel.decrementCounter()
        }

        // Decrement button onLongClick
        decrementButton.setOnLongClickListener {
            viewModel.decrementCounterBy5()
            false
        }
    }
}