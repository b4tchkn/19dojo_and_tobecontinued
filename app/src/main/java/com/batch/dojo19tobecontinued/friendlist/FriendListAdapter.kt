package com.batch.dojo19tobecontinued.friendlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.batch.dojo19tobecontinued.R
import com.batch.dojo19tobecontinued.databinding.FriendlistItemBinding
import com.batch.dojo19tobecontinued.friendlist.model.Friend
import com.batch.dojo19tobecontinued.friendlist.model.User

class FriendListAdapter : RecyclerView.Adapter<FriendListAdapter.FriendListViewHolder>() {

//    lateinit var listener: AdapterView.OnItemClickListener
    private var friends = emptyList<Friend>()

    class FriendListViewHolder(var view: FriendlistItemBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendListViewHolder {
//        setOnItemClickListener(listener)
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

//    fun setOnItemClickListener(listener: AdapterView.OnItemClickListener) {
//        this.listener = listener
//    }

    internal fun setFriends(friends: List<Friend>) {
        this.friends = friends
        notifyDataSetChanged()
    }
}