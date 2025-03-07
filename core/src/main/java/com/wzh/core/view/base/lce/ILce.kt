package com.wzh.core.view.base.lce

import android.view.View
import com.wzh.core.Play
import com.wzh.core.R

interface ILce {
    fun startLoading()
    fun loadFinished()
    fun showLoadErrorView(tip: String = Play.context?.getString(R.string.failed_load_data)?:"Failed to load data")
    fun showBadNetworkView(listener: View.OnClickListener)
    fun showNoContentView(tip: String)
}