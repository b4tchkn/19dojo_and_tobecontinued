package com.batch.dojo19tobecontinued.friendlist

import com.batch.dojo19tobecontinued.friendlist.model.User
import java.lang.Exception

data class FriendListState (
    val friendList: List<User>,
    val isOpenCameraSuccess: Boolean,
    val isReadSuccess: Boolean
) {
    companion object {
        val INITIAL = FriendListState(
            friendList = emptyList(),
            isOpenCameraSuccess = false,
            isReadSuccess = false
        )
    }
}