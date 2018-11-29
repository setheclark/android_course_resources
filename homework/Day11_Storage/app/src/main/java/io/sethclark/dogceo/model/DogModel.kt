package io.sethclark.dogceo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

class RandomDogImage(val status: String, val message: String)
class DogBreeds(val status: String, val message: Map<String, List<String>>)

//open class Breed(val name: String)
//class SubBreed(name: String, val parent: String) : Breed(name)


@Entity(tableName = "breeds")
data class Breed(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "sub_breed") val subBreed: String? = null,
    @ColumnInfo(name = "favorite") var favorite: Boolean = false
) {
    fun isSubBreed() = subBreed != null
}