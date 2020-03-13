package com.batch.dojo19tobecontinued.friendlist.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.batch.dojo19tobecontinued.friendlist.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getUsers(): LiveData<List<User>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)
}