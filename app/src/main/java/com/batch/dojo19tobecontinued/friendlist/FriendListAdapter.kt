package com.batch.dojo19tobecontinued.friendlist

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
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
        holder.view.twitterButton.setOnClickListener {
            val twitterUri = "https://twitter.com/${friends[position].twitterID}".toUri()
            openCustomTabs(holder.view.twitterButton.context, twitterUri)
        }
        holder.view.githubButton.setOnClickListener {
            val githubUri = "https://github.com/${friends[position].githubID}".toUri()
            openCustomTabs(holder.view.githubButton.context, githubUri)
        }
    }

    private fun openCustomTabs(context: Context, uri: Uri) {
        val tabsIntent = CustomTabsIntent.Builder()
            .setShowTitle(true)
            .setToolbarColor(context.getColor(R.color.colorPrimary))
            .build()
        tabsIntent.launchUrl(context, uri)
    }


    fun setFriends(friends: List<Friend>) {
        this.friends = friends
        notifyDataSetChanged()
    }
}