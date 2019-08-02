package com.jenyakirmiza.myapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
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
            val factory = SafeHelperFactory("asd23qwe1".toByteArray(Charsets.UTF_8), null as String?)

            val db = Room.databaseBuilder(context, AppDatabase::class.java, "sample-db")
                .openHelperFactory(factory)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        //TODO in case we want to insert some data on database creation stage
                    }
                })
                .build()

            return db
        }
    }

}
