package io.sethclark.networkingclasscode.api

import io.sethclark.networkingclasscode.model.RandomDogResponse
import retrofit2.Call
import retrofit2.http.GET


const val BASE_URL = "https://dog.ceo/api/"

interface DogApi {

    @GET("breeds/image/random")
    fun fetchRandomDogImage(): Call<RandomDogResponse>
}