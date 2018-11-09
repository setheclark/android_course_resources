package io.sethclark.day6_lifecycle

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

const val NAME_REQUEST_CODE = 123
const val EXTRA_NAME = "name"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_enterName.setOnClickListener {
            startActivityForResult(
                Intent(this, EnterNameActivity::class.java).putExtra(
                    EXTRA_NAME,
                    textview_result.text.toString()
                ), 123
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            NAME_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    textview_result.text = data?.getStringExtra(EXTRA_NAME)
                }
            }
        }
    }
}
