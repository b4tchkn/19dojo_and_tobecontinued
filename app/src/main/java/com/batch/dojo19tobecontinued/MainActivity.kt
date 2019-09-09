package com.batch.dojo19tobecontinued

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.room.Room
import com.batch.dojo19tobecontinued.model.MyDatabase
import com.batch.dojo19tobecontinued.model.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pager.adapter = TabAdapter(supportFragmentManager, this)
        tab_layout.setViewPager(pager)

        val data: Uri? = intent?.data
        Log.d("DATA", data.toString())

        val db = Room.databaseBuilder(applicationContext, MyDatabase::class.java, "user")
            .build()

        if (data != null) {
            val uri = Uri.parse(data.toString())
            val iam = uri.getQueryParameter("iam")
            val tw = uri.getQueryParameter("tw")
            val gh = uri.getQueryParameter("gh")

            val user = User()
            user.fullName = iam.toString()
            user.twitter = tw.toString()
            user.gitHub = gh.toString()

            Toast.makeText(this, "Add user ${iam}", Toast.LENGTH_LONG).show()

            thread {
                db.userDao().insert(user)
            }
        }

    }
}
