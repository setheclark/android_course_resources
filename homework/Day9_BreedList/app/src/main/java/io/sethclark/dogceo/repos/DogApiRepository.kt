package io.sethclark.dogceo.repos

import io.sethclark.dogceo.api.DogApi
import io.sethclark.dogceo.model.DogBreeds
import io.sethclark.dogceo.model.RandomDogImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DogApiRepository(private val dogApi: DogApi) {

    fun requestRandomDogImage(
        breed: String? = null,
        subbreed: String? = null,
        listener: (response: DogImageResponse) -> Unit
    ) {

        when {
            subbreed != null && breed != null -> dogApi.randomDogImageUrl(breed, subbreed)
            breed != null -> dogApi.randomDogImageUrl(breed)
            else -> dogApi.randomDogImageUrl()
        }.enqueue(object : Callback<RandomDogImage> {
            override fun onFailure(call: Call<RandomDogImage>, t: Throwable) {
                listener.invoke(DogImageResponse.Error)
            }

            override fun onResponse(call: Call<RandomDogImage>, response: Response<RandomDogImage>) {
                listener.invoke(DogImageResponse.ImageSuccess(response.body()!!))
            }

        })
    }

    fun requestDogBreeds(listener: (response: DogBreedResponse) -> Unit) {
        dogApi.breedList().enqueue(object : Callback<DogBreeds> {
            override fun onFailure(call: Call<DogBreeds>, t: Throwable) {
                listener.invoke(DogBreedResponse.Error)
            }

            override fun onResponse(call: Call<DogBreeds>, response: Response<DogBreeds>) {
                listener.invoke(DogBreedResponse.BreedsSuccess(response.body()!!))
            }

        })
    }

    sealed class DogImageResponse {
        object Error : DogImageResponse()
        data class ImageSuccess(val response: RandomDogImage) : DogImageResponse()

    }

    sealed class DogBreedResponse {
        object Error : DogBreedResponse()
        data class BreedsSuccess(val response: DogBreeds) : DogBreedResponse()
    }
}