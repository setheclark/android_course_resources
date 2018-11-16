package io.sethclark.networkingclasscode

import io.sethclark.networkingclasscode.api.BASE_URL
import io.sethclark.networkingclasscode.api.DogApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object Injection {

    val retrofit = Retrofit.Builder().apply {
        baseUrl(BASE_URL)
        addConverterFactory(MoshiConverterFactory.create())
    }.build()

    private fun provideDogRepository(): DogRepository {
        return DogRepository(retrofit.create(DogApi::class.java))
    }

    fun provideViewModelFactory(): ViewModelFactory {
        return ViewModelFactory(provideDogRepository())
    }
}