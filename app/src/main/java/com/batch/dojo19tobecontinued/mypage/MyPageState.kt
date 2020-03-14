package com.batch.dojo19tobecontinued.mypage

import android.graphics.Bitmap

data class MyPageState(
    val isFullNameEmpty: Boolean,
    val isGithubIDEmpty: Boolean,
    val isTwitterIDEmpty: Boolean,
    val qrBitmap: Bitmap?
) {
    companion object {
        val INITIAL = MyPageState(
            isFullNameEmpty = false,
            isGithubIDEmpty = false,
            isTwitterIDEmpty = false,
            qrBitmap = null
        )
    }
}