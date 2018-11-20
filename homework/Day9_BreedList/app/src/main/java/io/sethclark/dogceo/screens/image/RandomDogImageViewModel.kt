package io.sethclark.dogceo.screens.image

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.sethclark.dogceo.repos.DogApiRepository

class RandomDogImageViewModel(private val dogApiRepository: DogApiRepository) : ViewModel() {

    private val _screenState = MutableLiveData<ScreenState>().apply {
        value = ScreenState.LoadingDogUrl
    }

    val screenState: LiveData<ScreenState> = _screenState

    fun setBreed(breed: String?, subBreed: String? = null) {
        _screenState.value = ScreenState.LoadingDogUrl
        dogApiRepository.requestRandomDogImage(breed, subBreed) { response ->
            when (response) {
                is DogApiRepository.DogImageResponse.Error -> _screenState.value =
                        ScreenState.ErrorMessage
                is DogApiRepository.DogImageResponse.ImageSuccess -> {
                    if (response.response.status == "success") {
                        _screenState.value =
                                ScreenState.LoadDogImage(
                                    response.response.message
                                )
                    } else {
                        _screenState.value =
                                ScreenState.ErrorMessage
                    }
                }
            }
        }
    }


    sealed class ScreenState {
        object LoadingDogUrl : ScreenState()
        object ErrorMessage : ScreenState()
        data class LoadDogImage(val response: String) : ScreenState()
    }
}