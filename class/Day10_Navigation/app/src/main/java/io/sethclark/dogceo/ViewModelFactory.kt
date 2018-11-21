package io.sethclark.dogceo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.sethclark.dogceo.repos.DogApiRepository
import io.sethclark.dogceo.screens.breeds.BreedListViewModel
import io.sethclark.dogceo.screens.image.RandomDogImageViewModel

class ViewModelFactory(private val dogRepo: DogApiRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(BreedListViewModel::class.java) -> BreedListViewModel(dogRepo) as T
            modelClass.isAssignableFrom(RandomDogImageViewModel::class.java) -> RandomDogImageViewModel(dogRepo) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }

    }

}