package com.batch.dojo19tobecontinued.friendlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batch.dojo19tobecontinued.R
import com.batch.dojo19tobecontinued.databinding.FriendlistItemBinding
import com.batch.dojo19tobecontinued.friendlist.model.Friend

class FriendListAdapter : RecyclerView.Adapter<FriendListAdapter.FriendListViewHolder>() {

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
        holder.view.friend = friends[position]
    }


    internal fun setFriends(friends: List<Friend>) {
        this.friends = friends
        notifyDataSetChanged()
    }
}