package com.batch.dojo19tobecontinued.friendlist

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.*
import androidx.room.Room
import com.batch.dojo19tobecontinued.friendlist.model.MyDatabase
import com.batch.dojo19tobecontinued.friendlist.model.User
import kotlinx.coroutines.launch

class FriendListViewModel : ViewModel() {
    private val _state = MutableLiveData(FriendListState.INITIAL)
    val state: LiveData<FriendListState> = _state

    fun addFriend() {
        try {
            _state.value = _state.value?.copy(isOpenCameraSuccess = true)
        } catch (e: Exception) {
            _state.value = _state.value?.copy(isOpenCameraSuccess = false)
        }
    }

    fun fetchFriendList(context: Context, owner: LifecycleOwner) {
        val db = Room.databaseBuilder(context, MyDatabase::class.java, "user").build()
        db.userDao().getUsers().observe(owner, Observer {
            _state.value = _state.value?.copy(friendList = it)
        })
    }

    private fun saveFriend(context: Context, data: Intent?) {
        val content = data?.getStringExtra("SCAN_RESULT")
        val uri = Uri.parse(content)
        val fullName = uri.getQueryParameter("iam").toString()
        val githubID = uri.getQueryParameter("gh").toString()
        val twitterID = uri.getQueryParameter("tw").toString()

        val db = Room.databaseBuilder(context, MyDatabase::class.java, "user").build()
        val user = User(
            fullName = fullName,
            gitHub = githubID,
            twitter = twitterID
        )

        viewModelScope.launch {
            db.userDao().insert(user)
        }
    }

    fun getQRReadResult(context: Context, requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            _state.value = _state.value?.copy(isReadSuccess = true)
            saveFriend(context, data)
        } else {
            _state.value = _state.value?.copy(isReadSuccess = false)
        }
    }
}