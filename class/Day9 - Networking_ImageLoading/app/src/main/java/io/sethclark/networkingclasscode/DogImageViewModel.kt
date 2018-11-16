package io.sethclark.networkingclasscode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DogImageViewModel(private val dogRepo: DogRepository) : ViewModel() {

    private val _screenState = MutableLiveData<ScreenState>()
    val screenState: LiveData<ScreenState> = _screenState

    init {
        _screenState.value = ScreenState.Loading
        dogRepo.getRandomDogImage {
            when (it) {
                is DogRepository.DogImageResponse.Error -> {
                    _screenState.value = ScreenState.Error("Failed request")
                }
                is DogRepository.DogImageResponse.Success -> {
                    _screenState.value = ScreenState.LoadImage(it.payload)
                }
            }

        }
    }

    sealed class ScreenState {
        object Loading : ScreenState()
        class Error(val errorText: String) : ScreenState()
        class LoadImage(val url: String) : ScreenState()
    }
}