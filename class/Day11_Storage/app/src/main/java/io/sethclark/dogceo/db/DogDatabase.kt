package io.sethclark.dogceo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.sethclark.dogceo.dao.BreedDao
import io.sethclark.dogceo.model.Breed

@Database(entities = [Breed::class], version = 1)
abstract class DogDatabase : RoomDatabase() {

    abstract fun breedDao(): BreedDao

    companion object {
        @Volatile
        private var INSTANCE: DogDatabase? = null

        fun getDatabase(context: Context): DogDatabase {
            if (INSTANCE != null) {
                return INSTANCE as DogDatabase
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DogDatabase::class.java,
                    "dog_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}