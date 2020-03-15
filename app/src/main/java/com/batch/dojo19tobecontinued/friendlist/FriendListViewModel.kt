package com.batch.dojo19tobecontinued.friendlist

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
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

    //    lateinit var allFriends: LiveData<List<Friend>>
    private lateinit var repository: FriendRepository

    private val job = Job()
    private val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    fun loadDatabase(context: Context) {
        val friendDao = AppDatabase.getFriendDatabase(context).friendDao()
        repository = FriendRepository(friendDao)
        _state.value = _state.value?.copy(friendList = repository.allFriends.value)
    }
//        allFriends = repository.allFriends

//    fun addFriendTest() {
//        val friend = Friend(
//            fullName = "HOGE${_state.value?.friendList?.value?.size}",
//            githubID = "@githubhoge",
//            twitterID = "twitterhoge"
//        )
//        scope.launch(Dispatchers.IO) {
//            repository.insert(friend)
//        }
//        _state.value = _state.value?.copy(friendList = repository.allFriends)
//        allFriends = repository.allFriends
//    }

    fun openCamera(activity: Activity) {
        try {
            val intent = Intent("com.google.zxing.client.android.SCAN")
            intent.putExtra("SCANMODE", "QR_CODE_MODE")
            activity.startActivityForResult(intent, 0)
        } catch (e: Exception) {
            val marketUri = Uri.parse("market://details?id=com.google.zxing.client.android")
            val marketIntent = Intent(Intent.ACTION_VIEW, marketUri)
            activity.startActivity(marketIntent)
        }
    }

    fun saveFriend(data: Intent) {
        val content = data.getStringExtra("SCAN_RESLT")
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
            repository.insert(friend)
        }
        _state.value = _state.value?.copy(friendList = repository.allFriends.value)
    }

    fun getQRReadResult(context: Context, requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            _state.value = _state.value?.copy(isReadSuccess = true, readResultData = data)
            Toast.makeText(context, data.toString(), Toast.LENGTH_SHORT).show()
        } else {
            _state.value = _state.value?.copy(isReadSuccess = false, readResultData = null)
            Toast.makeText(context, "読み取り失敗", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
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
}