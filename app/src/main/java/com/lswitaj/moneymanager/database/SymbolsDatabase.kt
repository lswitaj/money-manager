package com.lswitaj.moneymanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//TODO(use a repository pattern)
//TODO(set exportSchema to true later on)
@Database(entities = [SymbolsOverview::class], version = 4, exportSchema = false)
abstract class SymbolsDatabase : RoomDatabase() {

    abstract val symbolsDatabaseDao: SymbolsDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: SymbolsDatabase? = null

        fun getInstance(context: Context): SymbolsDatabase {
            synchronized(this) {
                var instance = INSTANCE
                //TODO("adjust the wallet name here")
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SymbolsDatabase::class.java,
                        "wallet_database"
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