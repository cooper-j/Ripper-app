package com.hexan.ripper.manager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.hexan.ripper.R
import java.util.*

/**
 * Created by James Cooper on 19/04/2018.
 */
class FragmentStackManager(val supportFragmentManager: FragmentManager) {

    companion object {

        private var fragmentStackManager: FragmentStackManager? = null

        fun init(supportFragmentManager: FragmentManager): FragmentStackManager {
            if (this.fragmentStackManager == null) {
                this.fragmentStackManager = FragmentStackManager(supportFragmentManager)
            }
            return this.fragmentStackManager!!
        }
    }

    private val fragmentStack = Stack<Fragment>()

    fun setFragmentToFront(fragment: Fragment, tag: String) {
        if (fragmentStack.contains(fragment)) {
            while (fragmentStack.peek() != fragment) {
                popFragment()
            }
        } else {
            setFragment(fragment, tag)
        }
    }

    fun setFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.contentLayout, fragment, tag)
                .addToBackStack(tag)
                .commit()
        fragmentStack.clear()
        fragmentStack.push(fragment)
    }

    fun addFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
                .beginTransaction()
                .add(R.id.contentLayout, fragment, tag)
                .addToBackStack(tag)
                .commitAllowingStateLoss()
        fragmentStack.push(fragment)
    }

    fun popFragment() {
        supportFragmentManager.beginTransaction()
                .remove(fragmentStack.pop())
                .commitAllowingStateLoss()
        supportFragmentManager.popBackStack()
    }
}