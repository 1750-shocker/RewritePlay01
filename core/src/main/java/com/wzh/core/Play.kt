package com.wzh.core

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.wzh.core.util.DataStoreUtils

@SuppressLint("StaticFieldLeak")
object Play {
    private const val TAG = "Play"
    private const val USERNAME = "username"
    private const val NICK_NAME = "nickname"
    private const val IS_LOGIN = "isLogin"
    private lateinit var dataStore: DataStoreUtils

    /**
     * 获取全局Context
     */
   var context: Context? =null
        private set

    /**
     * 初始化,传入Application的context
     */
    fun initialize(c: Context?){
        if(c==null){
            Log.w(TAG, "initailize: context is null")
            return
        }
        context = c
        context?.apply{
            dataStore = DataStoreUtils.init(this)
        }
    }
}