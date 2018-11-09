package io.sethclark.day6_lifecycle

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_enter_name.*

class EnterNameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_name)

        if (savedInstanceState == null) {
            input_name.setText(intent.getStringExtra(EXTRA_NAME), TextView.BufferType.EDITABLE)
        }


        button_submit.setOnClickListener {
            setResult(Activity.RESULT_OK, Intent().apply { putExtra(EXTRA_NAME, input_name.text.toString()) })
            finish()
        }
    }
}
