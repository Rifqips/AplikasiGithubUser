package com.rifqipadisiliwangi.aplikasigithubuser.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rifqipadisiliwangi.aplikasigithubuser.view.detail.fragment.FollowersFragment
import com.rifqipadisiliwangi.aplikasigithubuser.view.detail.fragment.FollowingFragment

class FollowPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}