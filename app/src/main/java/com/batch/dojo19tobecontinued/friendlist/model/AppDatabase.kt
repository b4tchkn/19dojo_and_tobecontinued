package com.batch.dojo19tobecontinued.friendlist.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Friend::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun friendDao(): FriendDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getFriendDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "friend"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}