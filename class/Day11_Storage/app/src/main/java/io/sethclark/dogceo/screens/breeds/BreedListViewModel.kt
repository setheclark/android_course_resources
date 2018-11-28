package io.sethclark.dogceo.screens.breeds

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.sethclark.dogceo.model.Breed
import io.sethclark.dogceo.repos.DogApiRepository

class BreedListViewModel(val repo: DogApiRepository) : ViewModel() {

    val breeds: LiveData<List<Breed>> = repo.getDogBreeds()
}