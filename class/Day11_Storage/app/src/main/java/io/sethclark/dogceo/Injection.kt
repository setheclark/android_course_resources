package io.sethclark.dogceo

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import io.sethclark.dogceo.api.DogApi
import io.sethclark.dogceo.db.DogDatabase
import io.sethclark.dogceo.repos.DogApiRepository
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.Executors

object Injection {

    private val executor = Executors.newFixedThreadPool(1)

    fun provideAppSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("dog_prefs", Context.MODE_PRIVATE)
    }

    private val retrofit =
        Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .build()

    private val dogApi: DogApi = retrofit.create(DogApi::class.java)


    private fun provideDogRepo(context: Context): DogApiRepository {
        return DogApiRepository(
            dogApi,
            DogDatabase.getDatabase(context).breedDao(),
            executor
        )
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        val dogRepo = provideDogRepo(context)
        return ViewModelFactory(dogRepo)
    }
}