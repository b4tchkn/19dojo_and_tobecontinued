package com.batch.dojo19tobecontinued.friendlist

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.batch.dojo19tobecontinued.friendlist.model.Friend
import com.batch.dojo19tobecontinued.friendlist.model.AppDatabase
import com.batch.dojo19tobecontinued.friendlist.model.FriendDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FriendListViewModel : ViewModel() {

    var allFriends = MutableLiveData<List<Friend>>()
    private lateinit var repository: FriendRepository

    private val job = Job()
    private val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    fun loadDatabase(context: Context) {
        val friendDao = AppDatabase.getFriendDatabase(context).friendDao()
        repository = FriendRepository(friendDao)
        allFriends.value = repository.allFriends.value
    }

    fun addFriend() {
        val friend = Friend(fullName = "HOGE", githubID = "@githubhoge", twitterID = "twitterhoge")
//        val friendDao = AppDatabase.getFriendDatabase(context).friendDao()
//        val repository = FriendRepository(friendDao)
        scope.launch(Dispatchers.IO) {
            repository.insert(friend)
        }
        allFriends.value = repository.allFriends.value
    }

//    fun fetchFriendList(context: Context, owner: LifecycleOwner) {
//        val db = Room.databaseBuilder(context, MyDatabase::class.java, "user").build()
//        db.userDao().getUsers().observe(owner, Observer {
//            _state.value = _state.value?.copy(friendList = it)
//        })
//    }

//    private fun saveFriend(context: Context, data: Intent?) {
//        val content = data?.getStringExtra("SCAN_RESULT")
//        val uri = Uri.parse(content)
//        val fullName = uri.getQueryParameter("iam").toString()
//        val githubID = uri.getQueryParameter("gh").toString()
//        val twitterID = uri.getQueryParameter("tw").toString()
//
//        val db = Room.databaseBuilder(context, MyDatabase::class.java, "user").build()
//        val user = User(
//            fullName = fullName,
//            gitHub = githubID,
//            twitter = twitterID
//        )
//
//        viewModelScope.launch {
//            db.userDao().insert(user)
//        }
//    }

//    fun getQRReadResult(context: Context, requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
//            _state.value = _state.value?.copy(isReadSuccess = true)
//            saveFriend(context, data)
//        } else {
//            _state.value = _state.value?.copy(isReadSuccess = false)
//        }
//    }
}