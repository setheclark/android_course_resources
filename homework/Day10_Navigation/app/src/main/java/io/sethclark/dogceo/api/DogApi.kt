package io.sethclark.dogceo.api

import io.sethclark.dogceo.model.DogBreeds
import io.sethclark.dogceo.model.RandomDogImage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApi {

    @GET("breeds/image/random")
    fun randomDogImageUrl(): Call<RandomDogImage>

    @GET("breed/{breed}/images/random")
    fun randomDogImageUrl(@Path("breed") breed: String): Call<RandomDogImage>


    @GET("breed/{breed}/{subBreed}/images/random")
    fun randomDogImageUrl(@Path("breed") breed: String, @Path("subBreed") subBreed: String): Call<RandomDogImage>

    @GET("breeds/list/all")
    fun breedList(): Call<DogBreeds>
}