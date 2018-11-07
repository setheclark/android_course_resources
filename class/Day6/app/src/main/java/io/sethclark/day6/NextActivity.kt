package io.sethclark.day6

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View

class NextActivity : AppCompatActivity() {

    private val tag = "NextActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next)

        findViewById<View>(R.id.button).setOnClickListener {
            // Create the Intent used to return a value to MainActivity.  Using apply to add extras to the Intent
            val returnedIntent = Intent().apply {
                // Add the return value to the intent
                putExtra(RESULT_KEY_VALUE_RETURNED, "HORNETS WON!")
            }
            // Set the result to be passed back to MainActivity
            setResult(Activity.RESULT_OK, returnedIntent)
            //Call finish to close the current Activity which will return the user to MainActivity
            finish()
        }

        Log.e(tag, intent.getStringExtra(EXTRA_NAME) ?: "No value was passed in")
        Log.d(tag, "Hey, I'm in next.")
    }
}
