package com.lswitaj.moneymanager.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//TODO(use a repository pattern)
//TODO(set exportSchema to true later on)
@Database(entities = [Position::class], version = 2, exportSchema = false)
abstract class PositionsDatabase : RoomDatabase() {

    abstract val positionsDatabaseDao: PositionsDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: PositionsDatabase? = null

        fun getInstance(context: Context): PositionsDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PositionsDatabase::class.java,
                        "money_manager_database"
                    )
                        //TODO("add an option to migrate the db")
                        // https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}