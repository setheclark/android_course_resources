package io.sethclark.day7

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    // This is a private field for our MutableLiveData that will keep track of
    // our counter.
    private val _counter = MutableLiveData<Int>().apply {
        value = 0
    }
    // Since we only want out UI (Activity) to observe changes to our counter value
    // and not be able to make changes we will cast our private MutableLiveDate(_count)
    // to a public LiveData.  Data held in a LiveData can only be retrieved, or observed
    // for changes.
    val counter: LiveData<Int> = _counter

    fun incrementCounter() {
        // Because the value held by LiveData is nullable we need to provide a default
        // value when it is.  This is done using the '?:' operator which will return
        // the value on the right side of the operator if the value on the left is null.
        val currentCountValue = _counter.value ?: 0

        // Now that we have the value that is currently held by _counter (or 0) we can
        // add 1 to it and assign the result back to the _counter value.
        _counter.value = currentCountValue + 1
    }

    fun decrementCounter() {
        _counter.value = (_counter.value ?: 0) - 1
    }

    fun incrementCounterBy5() {
        _counter.value = (_counter.value ?: 0) + 5
    }

    fun decrementCounterBy5() {
        _counter.value = (_counter.value ?: 0) - 5
    }

    fun resetCounter() {
        _counter.value = 0
    }
}