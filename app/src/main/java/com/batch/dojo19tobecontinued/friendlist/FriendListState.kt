package com.batch.dojo19tobecontinued.friendlist

import android.content.Intent
import com.batch.dojo19tobecontinued.friendlist.model.Friend

data class FriendListState(
    val friendList: List<Friend>?,
    val isReadSuccess: Boolean,
    val readResultData: Intent?
) {
    companion object {
        val INITIAL = FriendListState(
            friendList = null,
            isReadSuccess = false,
            readResultData = null
        )
    }
}