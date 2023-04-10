package com.example.android.politicalpreparedness.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.android.politicalpreparedness.network.models.Election

@Database(entities = [Election::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ElectionDatabase : RoomDatabase() {

    abstract val electionDao: ElectionDao

    companion object {
        private const val databaseName = "election_database"

        fun buildDatabase(context: Context): ElectionDatabase {
            return Room.databaseBuilder(context, ElectionDatabase::class.java, databaseName)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}