package com.batch.dojo19tobecontinued.friendlist

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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.batch.dojo19tobecontinued.R
import com.batch.dojo19tobecontinued.friendlist.model.Friend
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
//        add_friend_button.setOnClickListener { viewModel.addFriendTest() }

        add_friend_button.setOnClickListener {
            viewModel.openCamera(requireActivity())
        }
//        viewModel.allFriends.observe(this, Observer { friends ->
//            friends?.let {
//                friendListAdapter.setFriends(it)
//            }
//        })

        viewModel.state.observe(this, Observer {
            checkState(it)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Toast.makeText(requireContext(), "読み取ったよ", Toast.LENGTH_SHORT).show()
        viewModel.getQRReadResult(requireContext(), requestCode, resultCode, data)
    }

    private fun checkState(state: FriendListState) {
        if (state.friendList != null) {
            friendListAdapter.setFriends(state.friendList)
        }
        if (state.isReadSuccess && state.readResultData != null) {
            viewModel.saveFriend(state.readResultData)
        }
    }
}