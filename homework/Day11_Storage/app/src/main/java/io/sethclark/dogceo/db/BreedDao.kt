package io.sethclark.dogceo.db

import androidx.lifecycle.LiveData
import androidx.room.*
import io.sethclark.dogceo.model.Breed

@Dao
interface BreedDao {

    @Insert
    fun insert(breed: Breed)

    @Query("SELECT * FROM breeds ORDER BY name ASC, sub_breed ASC")
    fun getBreeds(): LiveData<List<Breed>>

    @Query("SELECT count(*) FROM breeds")
    fun numberOfBreeds(): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateBreed(breed: Breed)

    @Query("SELECT * FROM breeds WHERE favorite=1 ORDER BY RANDOM() LIMIT 1")
    fun randomFavoriteBreed(): LiveData<Breed?>

    @Query("SELECT * FROM breeds WHERE id = :id")
    fun breedForId(id: Int): LiveData<Breed?>
}