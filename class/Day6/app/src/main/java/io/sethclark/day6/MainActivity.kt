package io.sethclark.day6

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast

// Reusable variable for the name value that is passed to NextActivity
const val EXTRA_NAME = "extra_name"
// Reusable variable that is used as the request code for starting NextActivity
const val NEXT_REQUEST_CODE = 123
// Reusable variable that is used by NextActivity to pass back a value
const val RESULT_KEY_VALUE_RETURNED = "returnedValue"

class MainActivity : AppCompatActivity() {

    // Reusable variable for log tags
    val tag = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(tag, " onCreate ${if (savedInstanceState == null) "null" else "not null"}")

        findViewById<View>(R.id.button_launchActivity).setOnClickListener {
            // Create Intent for NextActivity.  This will be used to navigate with startActivity
            val intent = Intent(this, NextActivity::class.java)

            // Add value that will be printed to logs during the next activities onCreate
            intent.putExtra(EXTRA_NAME, "Alfred")

            // Start NextActivity for result.  If successful this will return a String for us to print.
            startActivityForResult(intent, NEXT_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            // If the returning request was for NextActivity
            NEXT_REQUEST_CODE -> {
                // Create/show a Toast with information about the result
                Toast.makeText(
                    this, if (resultCode == RESULT_OK) {
                        "Result OK with returned value ${data?.getStringExtra(RESULT_KEY_VALUE_RETURNED)}"
                    } else {
                        "Result cancelled from NextActivity"
                    }, Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, " onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, " onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(tag, " onRestart")
    }

    override fun onPause() {
        super.onPause()
        Log.d(tag, " onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(tag, " onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, " onDestroy")
    }
}
