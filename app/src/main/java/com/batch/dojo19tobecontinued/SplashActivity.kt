package com.batch.dojo19tobecontinued

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.util.Log
import android.view.Window
import android.view.WindowManager

class SplashActivity : AppCompatActivity() {

    private var mDelayHandler: Handler? = null
    private val SPLASHDELAY: Long = 1500

    internal val mRunnable: Runnable = Runnable {

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        Log.d("WHEN", "MainActivity")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mDelayHandler = Handler()
        mDelayHandler!!.postDelayed(mRunnable, SPLASHDELAY)
    }
}
