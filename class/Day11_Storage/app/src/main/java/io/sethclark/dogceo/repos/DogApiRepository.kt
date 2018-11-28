package io.sethclark.dogceo.repos

import android.util.Log
import androidx.lifecycle.LiveData
import io.sethclark.dogceo.api.DogApi
import io.sethclark.dogceo.dao.BreedDao
import io.sethclark.dogceo.model.Breed
import io.sethclark.dogceo.model.RandomDogImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

class DogApiRepository(
    private val dogApi: DogApi,
    private val breedDao: BreedDao,
    private val executor: Executor
) {

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

    fun getDogBreeds(): LiveData<List<Breed>> {
        refreshDogBreeds()
        return breedDao.getBreeds()
    }

    private fun refreshDogBreeds() {
        executor.execute {
            if (breedDao.numberOfBreeds() == 0) {
                Log.d("REPO", "Refreshing breeds from network")
                val response = dogApi.breedList().execute()
                if (response.isSuccessful) {
                    response.body()!!.message.forEach { entry ->
                        breedDao.insert(Breed(name = entry.key))
                        entry.value.forEach { subBreed ->
                            breedDao.insert(Breed(name = entry.key, subBreed = subBreed))
                        }
                    }
                } else {
                    Log.e("REPO", "Breed list request failed: ${response.errorBody().toString()}")
                }
            }
        }
    }

    sealed class DogImageResponse {
        object Error : DogImageResponse()
        data class ImageSuccess(val response: RandomDogImage) : DogImageResponse()

    }
}