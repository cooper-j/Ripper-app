package com.hexan.ripper.ui.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.hexan.ripper.R
import com.hexan.ripper.manager.FragmentStackManager
import com.hexan.ripper.ui.main.playlist.PlaylistFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val registeredFragments = arrayOf(Fragment(), PlaylistFragment.newInstance(), Fragment())
    private val fragmentStackManager = FragmentStackManager.init(supportFragmentManager)

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                fragmentStackManager.setFragmentToFront(registeredFragments[0], "home")
                setPageTitle(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_playlist -> {
                fragmentStackManager.setFragmentToFront(registeredFragments[1], "playlist")
                setPageTitle(R.string.title_playlists)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {
                fragmentStackManager.setFragmentToFront(registeredFragments[2], "search")
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
            fragmentStackManager.popFragment()
        }
    }

    fun setPageTitle(titleResourceId: Int) {
        pagerPageTitleTextView.text = getString(titleResourceId)
    }

    fun setPageTitle(title: String) {
        pagerPageTitleTextView.text = title
    }

    fun addFragment(fragment: Fragment, name: String) {
        fragmentStackManager.addFragment(fragment, name)
    }
}
