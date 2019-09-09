package com.batch.dojo19tobecontinued.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.batch.dojo19tobecontinued.model.User
import com.batch.dojo19tobecontinued.model.UserDao

@Database(entities = [User::class], version = 1)
abstract class MyDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}