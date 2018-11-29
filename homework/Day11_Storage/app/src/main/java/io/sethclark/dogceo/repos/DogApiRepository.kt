package io.sethclark.dogceo.repos

import android.util.Log
import androidx.lifecycle.LiveData
import io.sethclark.dogceo.api.DogApi
import io.sethclark.dogceo.db.BreedDao
import io.sethclark.dogceo.model.Breed
import io.sethclark.dogceo.model.RandomDogImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

class DogApiRepository(private val dogApi: DogApi, private val breedDao: BreedDao, val executor: Executor) {

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


    fun updateBreed(breed: Breed) {
        executor.execute {
            breedDao.updateBreed(breed)
        }
    }

    fun getDogBreeds(): LiveData<List<Breed>> {
        refreshDogBreeds()
        return breedDao.getBreeds()
    }

    fun getBreedFromId(id: Int) =
        if (id >= 0) {
            breedDao.breedForId(id)
        } else {
            breedDao.randomFavoriteBreed()
        }


    private fun refreshDogBreeds() {
        executor.execute {
            if (breedDao.numberOfBreeds() == 0) {
                Log.d("REPO", "No breeds caches.  Making request.")
                val breedListResponse = dogApi.breedList().execute()
                if (breedListResponse.isSuccessful) {
                    val breedsMap = breedListResponse.body()!!.message
                    breedsMap.forEach {
                        val parent = it.key
                        breedDao.insert(Breed(name = parent))
                        it.value.forEach { sub ->
                            breedDao.insert(Breed(name = parent, subBreed = sub))
                        }
                    }
                } else {
                    Log.e("REPO", "Failed to fetch breeds: ${breedListResponse.message()}")
                }
            }
        }
    }

    sealed class DogImageResponse {
        object Error : DogImageResponse()
        data class ImageSuccess(val response: RandomDogImage) : DogImageResponse()
    }
}