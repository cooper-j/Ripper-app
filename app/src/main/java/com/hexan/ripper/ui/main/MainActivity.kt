package com.hexan.ripper.ui.main

import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.hexan.ripper.R
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.support.v7.app.AlertDialog
import android.util.SparseArray
import com.hexan.ripper.ui.main.playlist.PlaylistFragment


class MainActivity : AppCompatActivity() {
    var registeredFragments = arrayOf(Fragment(), PlaylistFragment.newInstance(), Fragment())

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                setFragment(registeredFragments[0], "home")
                setPageTitle(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_playlist -> {
                setFragment(registeredFragments[1], "playlist")
                setPageTitle(R.string.title_playlists)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {
                setFragment(registeredFragments[2], "search")
                setPageTitle(R.string.title_search)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onBackPressed() {

        if (supportFragmentManager.backStackEntryCount == 1) {
            AlertDialog.Builder(this)
                    .setTitle(R.string.exit_title)
                    .setMessage(R.string.exit_message)
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.ok, { dialog, which -> finish() })
                    .create()
                    .show()
        } else {
            super.onBackPressed()
        }
    }

    private fun setFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.contentLayout, fragment, tag)
                .addToBackStack(tag)
                .commit()
    }

    fun addFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
                .beginTransaction()
                .add(R.id.contentLayout, fragment, tag)
                .addToBackStack(tag)
                .commit()
    }

    fun setPageTitle(titleResourceId: Int) {
        pagerPageTitleTextView.text = getString(titleResourceId)
    }

    fun setPageTitle(title: String) {
        pagerPageTitleTextView.text = title
    }
}
