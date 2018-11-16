package io.sethclark.networkingclasscode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val dogRepo: DogRepository) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DogImageViewModel::class.java)) {
            return DogImageViewModel(dogRepo) as T
        }
        throw IllegalStateException("Unknown view model")
    }


}