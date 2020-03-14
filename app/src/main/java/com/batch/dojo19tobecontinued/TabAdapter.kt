package com.batch.dojo19tobecontinued

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.batch.dojo19tobecontinued.friendlist.FriendListFragment
import com.batch.dojo19tobecontinued.mypage.MyPageFragment

class TabAdapter(fm: FragmentManager, private val context: Context) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return MyPageFragment()
            }
            else -> {
                return FriendListFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> {
                return "Mypage"
            }
            else -> {
                return "FriendList"
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }
}