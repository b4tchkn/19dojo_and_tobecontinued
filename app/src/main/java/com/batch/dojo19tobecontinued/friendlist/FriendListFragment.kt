package com.batch.dojo19tobecontinued.friendlist

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.batch.dojo19tobecontinued.R
import com.batch.dojo19tobecontinued.friendlist.model.MyDatabase
import com.batch.dojo19tobecontinued.friendlist.model.User
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.android.synthetic.main.fragment_friendlist.*
import kotlin.concurrent.thread

class FriendListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_friendlist, container, false)

        //getUserDB()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUserDB()

        addFriendFloatingActionButton.setOnClickListener { onAddFriendButtonTapped() }
    }

    private fun onAddFriendButtonTapped() {
        try {
            val intent = Intent("com.google.zxing.client.android.SCAN")
            intent.putExtra("SCANMODE", "QR_CODE_MODE")
            startActivityForResult(intent, 0)
        } catch (e: Exception) {
            val marketUri = Uri.parse("market://details?id=com.google.zxing.client.android")
            val marketIntent = Intent(Intent.ACTION_VIEW, marketUri)
            startActivity(marketIntent)
        }
    }


    private fun getUserDB() {
        val db = Room.databaseBuilder(context!!, MyDatabase::class.java, "user").build()

        db.userDao().getUsers().observe(this, Observer {
            val profileList = it as MutableList<User>
            view?.findViewById<RecyclerView>(R.id.profileRecyclerView)
                .also { recyclerView: RecyclerView? ->
                    val alphaAdapter = AlphaInAnimationAdapter(
                        FrienViewAdapter(
                            view?.context!!,
                            profileList
                        )
                    )
                    recyclerView?.adapter = ScaleInAnimationAdapter(alphaAdapter).apply {
                        setDuration(500)
                        setHasStableIds(false)
                        setFirstOnly(false)
                        setInterpolator(OvershootInterpolator(.100f))
                    }
                    recyclerView?.layoutManager = LinearLayoutManager(view?.context!!)
                }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            // QRコード読み取り成功時の処理
            // 値保存したりとか…
            val content = data?.getStringExtra("SCAN_RESULT")

            val uri = Uri.parse(content)
            val iam = uri.getQueryParameter("iam")
            val gh = uri.getQueryParameter("gh")
            val tw = uri.getQueryParameter("tw")

            // Room
            val db = Room.databaseBuilder(context!!, MyDatabase::class.java, "user").build()

            val user = User()
            user.fullName = iam.toString()
            user.gitHub = gh.toString()
            user.twitter = tw.toString()

            Toast.makeText(context, content.toString(), Toast.LENGTH_LONG).show()

            thread {
                db.userDao().insert(user)
            }


            getUserDB()

        } else if (resultCode == Activity.RESULT_CANCELED) {
            // だめだった時の処理
            Toast.makeText(context, "QR Read Error", Toast.LENGTH_LONG).show()
        }
    }
}