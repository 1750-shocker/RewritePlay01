package com.wzh.core.view.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

interface BaseInit {

    fun initData()

    fun initView()

}

interface BaseActivityInit : BaseInit {

    fun getLayoutView(): View

}

interface BaseFragmentInit : BaseInit {

    fun getLayoutView(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean): View

}