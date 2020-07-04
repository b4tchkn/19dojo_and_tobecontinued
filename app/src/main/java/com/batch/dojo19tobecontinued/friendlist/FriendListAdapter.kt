package com.batch.dojo19tobecontinued.friendlist

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batch.dojo19tobecontinued.R
import com.batch.dojo19tobecontinued.databinding.FriendlistItemBinding
import com.batch.dojo19tobecontinued.friendlist.model.Friend

class FriendListAdapter(private val listener: OnMyItemClickListener) : RecyclerView.Adapter<FriendListAdapter.FriendListViewHolder>() {


    interface OnMyItemClickListener {
        fun onTwitterButtonClick(twitterUri: Uri)
        fun onGithubButtonClick(githubUri: Uri)
    }


    private var friends = emptyList<Friend>()

    class FriendListViewHolder(var view: FriendlistItemBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<FriendlistItemBinding>(
            inflater,
            R.layout.friendlist_item,
            parent,
            false
        )
        return FriendListViewHolder(view)
    }

    override fun getItemCount() = friends.size

    override fun onBindViewHolder(holder: FriendListViewHolder, position: Int) {
        val twitterUri = "https://twitter.com/${friends[position].twitterID}".toUri()
        val githubUri = "https://github.com/${friends[position].githubID}".toUri()

        holder.view.apply {
            friend = friends[position]
            twitterButton.setOnClickListener { listener.onTwitterButtonClick(twitterUri) }
            githubButton.setOnClickListener { listener.onGithubButtonClick(githubUri) }
        }
    }
        fun setFriends(friends: List<Friend>) {
            this.friends = friends
            notifyDataSetChanged()
    }
}