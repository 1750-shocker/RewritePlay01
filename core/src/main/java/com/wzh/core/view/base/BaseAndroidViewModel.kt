package com.wzh.core.view.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap

abstract class BaseAndroidViewModel<BaseData, Data, Key> : ViewModel() {
    val dataList = ArrayList<Data>()

    private val pageLiveData = MutableLiveData<Key>()

    fun getDataList(page: Key) {
        pageLiveData.value = page!!
    }
    //这个LiveData的值是一个Result（包裹真数据List）
    val dataLiveData = pageLiveData.switchMap { page ->
        getData(page)
    }
    //具体的ViewModel在改方法里调用具体的网络请求，返回一个LiveData
    abstract fun getData(page: Key): LiveData<Result<BaseData>>
}