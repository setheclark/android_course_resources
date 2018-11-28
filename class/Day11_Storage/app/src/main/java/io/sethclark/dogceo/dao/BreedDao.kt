package io.sethclark.dogceo.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import io.sethclark.dogceo.model.Breed

@Dao
interface BreedDao {

    @Insert
    fun insert(breed: Breed)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(breed: Breed)

    @Query("SELECT count(*) FROM breeds")
    fun numberOfBreeds(): Int

    @Query("SELECT * FROM breeds ORDER BY breed ASC, subBreed ASC")
    fun getBreeds(): LiveData<List<Breed>>

    @Query("SELECT * FROM breeds WHERE isFavorite=1 ORDER BY RANDOM() LIMIT 1")
    fun randomFavoriteBreed(): Breed
}