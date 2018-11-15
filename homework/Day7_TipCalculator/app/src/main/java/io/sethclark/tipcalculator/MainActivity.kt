package io.sethclark.tipcalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private val viewModel: TipViewModel by lazy { ViewModelProviders.of(this).get(TipViewModel::class.java) }
    private val currencyFormatter = DecimalFormat.getCurrencyInstance()

    private val tipTextView: TextView by lazy { findViewById<TextView>(R.id.textView_tipAmount) }
    private val totalTextView: TextView by lazy { findViewById<TextView>(R.id.textView_total) }
    private val percentageGroup: RadioGroup by lazy { findViewById<RadioGroup>(R.id.radioGroup_tipPercentage) }
    private val billEditText: EditText by lazy { findViewById<EditText>(R.id.editText_billAmount) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.setTipPercentage(tipPercentageForButtonId(percentageGroup.checkedRadioButtonId))
        percentageGroup.setOnCheckedChangeListener { _, checkedId ->
            viewModel.setTipPercentage(
                tipPercentageForButtonId(checkedId)
            )
        }

        billEditText.afterTextChanged {
            viewModel.setBillAmount(it.toDoubleOrNull())
        }

        viewModel.tipAmount.observe(this, Observer {
            tipTextView.text = if (it != null) currencyFormatter.format(it) else ""
        })

        viewModel.total.observe(this, Observer {
            totalTextView.text = if (it != null) currencyFormatter.format(it) else ""
        })
    }

    private fun tipPercentageForButtonId(checkedId: Int): Double {
        return when (checkedId) {
            R.id.radioButton_10Percent -> 10.0
            R.id.radioButton_15Percent -> 15.0
            R.id.radioButton_20Percent -> 20.0
            R.id.radioButton_25Percent -> 25.0
            else -> throw IllegalArgumentException("Unknown tip amount")
        }
    }
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}
