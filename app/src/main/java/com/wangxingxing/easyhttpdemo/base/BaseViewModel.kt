package com.wangxingxing.easyhttpdemo.base

import android.annotation.SuppressLint
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModel


/**
 * author : 王星星
 * date : 2023/10/25 12:05
 * email : 1099420259@qq.com
 * description :
 */
open class BaseViewModel : ViewModel(), LifecycleOwner {

    @SuppressLint("StaticFieldLeak")
    private val mLifecycle = LifecycleRegistry(this)

    init {
        mLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        mLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun onCleared() {
        super.onCleared()
        mLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

    override fun getLifecycle(): Lifecycle {
        return mLifecycle
    }
}