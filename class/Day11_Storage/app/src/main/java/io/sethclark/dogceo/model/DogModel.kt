package io.sethclark.dogceo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

class RandomDogImage(val status: String, val message: String)
class DogBreeds(val status: String, val message: Map<String, List<String>>)


@Entity(tableName = "breeds")
data class Breed(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "breed") val name: String,
    @ColumnInfo(name = "subBreed") val subBreed: String? = null,
    @ColumnInfo(name = "isFavorite") val isFavorite: Boolean = false
) {
    fun isSubBreed() = subBreed != null
}