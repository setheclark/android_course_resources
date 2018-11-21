package io.sethclark.dogceo

import com.squareup.moshi.Moshi
import io.sethclark.dogceo.api.DogApi
import io.sethclark.dogceo.repos.DogApiRepository
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Injection {

    private val retrofit =
        Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .build()

    private val dogApi: DogApi = retrofit.create(DogApi::class.java)


    private fun provideDogRepo(): DogApiRepository {
        return DogApiRepository(dogApi)
    }

    fun provideViewModelFactory(): ViewModelFactory {
        val dogRepo = provideDogRepo()
        return ViewModelFactory(dogRepo)
    }
}