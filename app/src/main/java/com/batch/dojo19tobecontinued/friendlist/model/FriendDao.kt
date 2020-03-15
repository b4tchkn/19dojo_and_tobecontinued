package com.batch.dojo19tobecontinued.friendlist.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FriendDao {
    @Query("SELECT * FROM friend")
    fun getAllFriends(): LiveData<List<Friend>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFriend(friend: Friend)
}