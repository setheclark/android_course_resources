package io.sethclark.tipcalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TipViewModel : ViewModel() {

    private var tipPercentage: Double? = null
    private var billAmount: Double? = null

    private val _total = MutableLiveData<Double?>()
    val total: LiveData<Double?> = _total

    private val _tipAmount = MutableLiveData<Double?>()
    val tipAmount: LiveData<Double?> = _tipAmount

    fun setTipPercentage(tipPercentage: Double?) {
        this.tipPercentage = tipPercentage
        calculateTip()
    }

    fun setBillAmount(billAmount: Double?) {
        this.billAmount = billAmount
        calculateTip()
    }

    private fun calculateTip() {
        val percent = tipPercentage
        val billAmt = billAmount
        if (billAmt == null || percent == null) {
            return
        }

        _tipAmount.value = billAmt * (percent / 100.0)
        _total.value = billAmt + (_tipAmount.value ?: 0.0)
    }

}