package io.sethclark.networkingclasscode

import io.sethclark.networkingclasscode.api.DogApi
import io.sethclark.networkingclasscode.model.RandomDogResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DogRepository(private val dogApi: DogApi) {

    fun getRandomDogImage(listener: (DogImageResponse) -> Unit) {

        val call = dogApi.fetchRandomDogImage()
        call.enqueue(object : Callback<RandomDogResponse> {
            override fun onFailure(call: Call<RandomDogResponse>, t: Throwable) {
                listener.invoke(DogImageResponse.Error)
            }

            override fun onResponse(call: Call<RandomDogResponse>, response: Response<RandomDogResponse>) {
                listener.invoke(DogImageResponse.Success(response.body()!!.message))
            }
        })
    }

    sealed class DogImageResponse {
        object Error : DogImageResponse()
        class Success(val payload: String) : DogImageResponse()
    }
}