package com.hexan.ripper.ui.main

import android.support.v4.app.Fragment

/**
 * Created by James Cooper on 16/04/2018.
 */
abstract class BaseFragment: Fragment() {
    fun setTitle(title: String) {
        (activity as MainActivity).setPageTitle(title)
    }
    fun setTitle(titleRessourceId: Int) {
        (activity as MainActivity).setPageTitle(titleRessourceId)
    }
}