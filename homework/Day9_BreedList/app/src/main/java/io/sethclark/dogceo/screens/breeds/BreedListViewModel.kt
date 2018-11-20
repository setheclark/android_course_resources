package io.sethclark.dogceo.screens.breeds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.sethclark.dogceo.model.Breed
import io.sethclark.dogceo.model.SubBreed
import io.sethclark.dogceo.repos.DogApiRepository

class BreedListViewModel(val repo: DogApiRepository) : ViewModel() {

    init {
        repo.requestDogBreeds { response ->
            when (response) {
                is DogApiRepository.DogBreedResponse.Error -> _screenState.value = ScreenState.ErrorMessage
                is DogApiRepository.DogBreedResponse.BreedsSuccess -> {
                    val breeds = mutableListOf<Breed>()
                    response.response.message.forEach { entry ->
                        breeds.add(Breed(entry.key))
                        entry.value.forEach { subBreed ->
                            breeds.add(SubBreed(subBreed, entry.key))
                        }
                    }

                    _screenState.value = ScreenState.DisplayBreedList(breeds)
                }
            }
        }
    }


    private val _screenState = MutableLiveData<ScreenState>().apply {
        value = ScreenState.LoadingBreedList
    }

    val screenState: LiveData<ScreenState> = _screenState

    sealed class ScreenState {
        object LoadingBreedList : ScreenState()
        object ErrorMessage : ScreenState()
        data class DisplayBreedList(val response: List<Breed>) : ScreenState()
    }
}