package com.batch.dojo19tobecontinued.friendlist

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.batch.dojo19tobecontinued.R
import com.batch.dojo19tobecontinued.WebViewActivity
import com.batch.dojo19tobecontinued.friendlist.model.User

class FrienViewAdapter(private val context: Context, private val profileList: MutableList<User>) :
    RecyclerView.Adapter<FrienViewAdapter.ProfileViewHolder>() {

    class ProfileViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fullNameText: TextView = view.findViewById(R.id.fullNameText)
        val gitHubButton: ImageButton = view.findViewById(R.id.gitHubButton)
        val twitterButton: ImageButton = view.findViewById(R.id.twitterButton)
        val gitHubAccountText: TextView = view.findViewById(R.id.gitHubAccountText)
        val twitterAccounttext: TextView = view.findViewById(R.id.twitterAccountText)
        // TODO: ほかにprofilelist_itemに表示項目ふやしたらHolderに持たす
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder =
        ProfileViewHolder(
            LayoutInflater.from(
                context
            ).inflate(R.layout.friendlist_item, parent, false)
        )

    override fun getItemCount() = profileList.size

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {

        holder.fullNameText.text = profileList[position].fullName
        // TODO: GitHub, Twitterの値をボタンに埋め込む

        holder.gitHubButton.setOnClickListener {
            // TODO: WebViewにとぶ
            Log.d("GITHUBLOG", "${position}が呼ばれたよ")
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("FLAG", "gh")
            intent.putExtra("GITHUBURL", "https://github.com/${profileList[position].gitHub}")
            context.startActivity(intent)
        }


        holder.gitHubAccountText.text = profileList[position].gitHub

        holder.twitterButton.setOnClickListener {
            // TODO: WebViewにとぶ
            Log.d("TWITTERLOG", "${position}が呼ばれたよ")
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("FLAG", "tw")
            intent.putExtra("TWITTERURL", "https://twitter.com/${profileList[position].twitter}")
            context.startActivity(intent)
        }

        holder.twitterAccounttext.text = profileList[position].twitter
    }
}