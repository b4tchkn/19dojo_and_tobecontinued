package com.batch.dojo19tobecontinued.main

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.batch.dojo19tobecontinued.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pagerAdapter = ScreenSlidePagerAdapter(this)
        pager.adapter = pagerAdapter

        TabLayoutMediator(tab_layout, pager) { tab, position ->
            when (position) {
                0 -> tab.text = getText(R.string.friend_list_title)
                else -> tab.text = getText(R.string.mypage_title)
            }
        }.attach()
    }

    override fun onBackPressed() {
        if (pager.currentItem == 0) {
            super.onBackPressed()
        } else {
            pager.currentItem = pager.currentItem - 1
        }
    }
}
