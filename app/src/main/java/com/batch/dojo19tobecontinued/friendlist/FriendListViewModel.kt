package com.batch.dojo19tobecontinued.friendlist

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.batch.dojo19tobecontinued.friendlist.model.AppDatabase
import com.batch.dojo19tobecontinued.friendlist.model.Friend
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FriendListViewModel : ViewModel() {

    private val _state = MutableLiveData(FriendListState.INITIAL)
    val state: LiveData<FriendListState> = _state

    lateinit var friendList: LiveData<List<Friend>>

    private lateinit var repository: FriendRepository
    private val job = Job()
    private val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    fun loadDatabase(context: Context) {
        val friendDao = AppDatabase.getFriendDatabase(context).friendDao()
        repository = FriendRepository(friendDao)
        Log.d("DojoApp-loadDatabase", "Roomの中${repository.allFriends}")
        Log.d("DojoApp-loadDatabase", "Roomの中${repository.allFriends.value}")
        friendList = repository.allFriends
//        _state.value = _state.value?.copy(friendList = repository.allFriends.value)
    }

    fun saveFriend(data: Intent) {
        val content = data.getStringExtra("SCAN_RESULT")
        val uri = Uri.parse(content)
        val fullName = uri.getQueryParameter("iam").toString()
        val githubID = uri.getQueryParameter("gh").toString()
        val twitterID = uri.getQueryParameter("tw").toString()
        val friend = Friend(
            fullName = fullName,
            githubID = githubID,
            twitterID = twitterID
        )
        scope.launch(Dispatchers.IO) {
            Log.d("DojoApp", "$friend をセーブします")
            repository.insert(friend)
        }
        friendList = repository.allFriends
//        _state.value = _state.value?.copy(
//            friendList = repository.allFriends.value,
//            isReadSuccess = false,
//            readResultData = null
//        )
    }

    fun getQRReadResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            _state.value = _state.value?.copy(isReadSuccess = true, readResultData = data)
        } else if (resultCode == Activity.RESULT_CANCELED) {
            _state.value = _state.value?.copy(isReadSuccess = false, readResultData = null)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}