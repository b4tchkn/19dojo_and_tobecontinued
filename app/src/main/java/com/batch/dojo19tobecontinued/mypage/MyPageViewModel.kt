package com.batch.dojo19tobecontinued.mypage

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.util.AndroidRuntimeException
import android.widget.Toast
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

    fun onCreateQR(
        activity: Activity,
        fullNameEditText: Editable?,
        githubIDEditText: Editable?,
        twitterIDEditText: Editable?
    ) {
        _state.value = _state.value?.copy(
            isFullNameEmpty = TextUtils.isEmpty(fullNameEditText),
            isGithubIDEmpty = TextUtils.isEmpty(githubIDEditText),
            isTwitterIDEmpty = TextUtils.isEmpty(twitterIDEditText),
            qrBitmap = null
        )
        if (TextUtils.isEmpty(fullNameEditText) &&
            TextUtils.isEmpty(githubIDEditText) &&
            TextUtils.isEmpty(twitterIDEditText)
        ) return

        val fullName = fullNameEditText.toString()
        val githubID = githubIDEditText.toString()
        val twitterID = twitterIDEditText.toString()

        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
        sharedPref.edit {
            putString(activity.getString(R.string.full_name_key), fullName)
            putString(activity.getString(R.string.github_key), githubID)
            putString(activity.getString(R.string.twitter_key), twitterID)
        }

        if (fullName.contains(" ")) {
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
            Toast.makeText(activity, qrData, Toast.LENGTH_SHORT).show()
        } catch (e: WriterException) {
            throw AndroidRuntimeException("Barcode Error", e)
        }
    }

    private fun String.replaceSpace(): String {
        return this.replace(" ", "%20")
    }
}