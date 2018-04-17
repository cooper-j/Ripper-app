package com.hexan.ripper.ui.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.hexan.ripper.R
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.view.ViewPager.OnPageChangeListener


class MainActivity : AppCompatActivity() {

    private lateinit var mainPagerAdapter: MainPagerAdapter

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                mainViewPager.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_playlist -> {
                mainViewPager.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {
                mainViewPager.currentItem = 2
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainPagerAdapter = MainPagerAdapter(supportFragmentManager)
        mainViewPager.adapter = mainPagerAdapter
        mainViewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        navigation.selectedItemId = R.id.navigation_home
                        setPageTitle(R.string.title_home)
                    }
                    1 -> {
                        navigation.selectedItemId = R.id.navigation_playlist
                        setPageTitle(R.string.title_playlists)
                    }
                    2 -> {
                        navigation.selectedItemId = R.id.navigation_search
                        setPageTitle(R.string.title_search)
                    }
                }
            }
        })

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    fun setPageTitle(titleResourceId: Int) {
        pagerPageTitleTextView.text = getString(titleResourceId)
    }

    fun setPageTitle(title: String) {
        pagerPageTitleTextView.text = title
    }

    override fun onBackPressed() {
        val currentFragment = mainPagerAdapter.getCurrentFragment(mainViewPager.currentItem)
        val childfm = currentFragment.childFragmentManager
        val fragmentStackSize = childfm.fragments.size
        if (fragmentStackSize > 0 && childfm.fragments[fragmentStackSize - 1].isVisible) {
            if (fragmentStackSize == 1)
                currentFragment.onResume()
            else
                childfm.fragments[fragmentStackSize - 2].onResume()
            childfm.popBackStack()
        }
        super.onBackPressed()
    }
}
