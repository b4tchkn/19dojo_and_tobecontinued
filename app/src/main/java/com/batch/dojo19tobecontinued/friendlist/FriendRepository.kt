package com.batch.dojo19tobecontinued.friendlist

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.batch.dojo19tobecontinued.friendlist.model.Friend
import com.batch.dojo19tobecontinued.friendlist.model.FriendDao

class FriendRepository(private val friendDao: FriendDao) {
    val allFriends: LiveData<List<Friend>> = friendDao.getAllFriends()

    @WorkerThread
    suspend fun insert(friend: Friend) {
        friendDao.insertFriend(friend)
    }
}