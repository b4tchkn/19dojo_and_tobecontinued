package com.batch.dojo19tobecontinued.mypage

import android.app.Activity
import android.content.Context
import android.util.AndroidRuntimeException
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.batch.dojo19tobecontinued.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder

class MyPageViewModel : ViewModel() {
    private val _state = MutableLiveData(MyPageState.INITIAL)
    val state: LiveData<MyPageState> = _state

    fun onCreateQR(activity: Activity, fullName: String?, githubID: String?, twitterID: String?) {
        if (fullName == null) {
            _state.value = _state.value?.copy(isFullNameEmpty = true)
            return
        } else {
            _state.value = _state.value?.copy(isFullNameEmpty = false)
        }

        if (githubID == null) {
            _state.value = _state.value?.copy(isGithubIDEmpty = true)
            return
        } else {
            _state.value = _state.value?.copy(isGithubIDEmpty = false)
        }

        if (twitterID == null) {
            _state.value = _state.value?.copy(isTwitterIDEmpty = true)
            return
        } else {
            _state.value = _state.value?.copy(isTwitterIDEmpty = false)
        }

        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        sharedPref.edit {
            putString(activity.getString(R.string.full_name_key), fullName)
            putString(activity.getString(R.string.github_key), githubID)
            putString(activity.getString(R.string.twitter_key), twitterID)
        }
        if (fullName.contains(" ")) {
//            fullName.replace(" ", "%20")??????
            fullName.replaceSpace()
        }

        val qrData = "ca-tech://dojo/share?iam=$fullName&tw=$twitterID&gh=$githubID"
        try {
            val barCodeEncoder = BarcodeEncoder()
            val bitmap = barCodeEncoder.encodeBitmap(
                qrData,
                BarcodeFormat.QR_CODE,
                500,
                500
            )
            _state.value = _state.value?.copy(qrBitmap = bitmap)
        } catch (e: WriterException) {
            throw AndroidRuntimeException("Barcode Error", e)
        }
    }

    private fun String.replaceSpace(): String {
        return this.replace(" ", "%20")
    }
}