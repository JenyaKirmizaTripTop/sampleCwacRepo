package com.jenyakirmiza.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.commonsware.cwac.saferoom.SafeHelperFactory

/**
 * The Room database for this app
 */
@Database(entities = [SampleTableEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun sampleDao(): SampleTableDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            val factory = SafeHelperFactory("asd23qwe1".toCharArray())

            val db = Room.databaseBuilder(context, AppDatabase::class.java, "sample-db")
                .openHelperFactory(factory)
                .fallbackToDestructiveMigration()
                .build()

            return db
        }
    }

}
