package com.batch.dojo19tobecontinued.friendlist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.batch.dojo19tobecontinued.R
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.android.synthetic.main.fragment_friendlist.*

class FriendListFragment : Fragment() {

    private val friendListAdapter = FriendListAdapter()

    private val viewModel: FriendListViewModel by lazy {
        ViewModelProvider(this).get(FriendListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_friendlist, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadDatabase(requireContext())

        friend_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            val alphaAdapter = AlphaInAnimationAdapter(friendListAdapter)
            adapter = ScaleInAnimationAdapter(alphaAdapter).apply {
                setDuration(500)
                setHasStableIds(false)
                setFirstOnly(false)
                setInterpolator(OvershootInterpolator(.100f))
            }
        }

        add_friend_button.setOnClickListener {
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

        viewModel.friendList.observe(this, Observer {
            Log.d("DojoApp", "friendList変更検知！中身$it")
            friendListAdapter.setFriends(it)
        })

        viewModel.state.observe(this, Observer {
            checkState(it)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        viewModel.getQRReadResult(requestCode, resultCode, data)
    }

    private fun checkState(state: FriendListState) {
        if (state.isReadSuccess && state.readResultData != null) {
            viewModel.saveFriend(state.readResultData)
        }
    }
}