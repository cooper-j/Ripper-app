package com.hexan.ripper.ui.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.hexan.ripper.ui.main.playlist.PlaylistFragment
import android.util.SparseArray



/**
 * Created by James Cooper on 11/04/2018.
 */
class MainPagerAdapter(fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager) {
    var registeredFragments = SparseArray<Fragment>()

    override fun getItem(position: Int): Fragment {
        val fragment = when (position) {
            0 -> Fragment()
            1 -> PlaylistFragment.newInstance()
            2 -> Fragment()
            else -> Fragment()
        }
        registeredFragments.put(position, fragment)
        return fragment
    }

    override fun getCount(): Int {
        return 3
    }

    fun getCurrentFragment(position: Int): Fragment {
        return registeredFragments[position]
    }
}