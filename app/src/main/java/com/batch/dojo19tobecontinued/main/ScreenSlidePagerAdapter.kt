package com.batch.dojo19tobecontinued.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.batch.dojo19tobecontinued.friendlist.FriendListFragment
import com.batch.dojo19tobecontinued.mypage.MyPageFragment

class ScreenSlidePagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MyPageFragment()
            else -> FriendListFragment()
        }
    }
}