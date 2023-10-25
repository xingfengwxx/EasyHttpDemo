package com.wangxingxing.easyhttpdemo

import androidx.lifecycle.MutableLiveData
import com.hjq.http.EasyHttp
import com.hjq.http.listener.OnHttpListener
import com.wangxingxing.easyhttpdemo.base.BaseViewModel
import com.wangxingxing.easyhttpdemo.http.api.HotKeyApi
import com.wangxingxing.easyhttpdemo.http.model.HttpData


/**
 * author : 王星星
 * date : 2023/10/25 12:08
 * email : 1099420259@qq.com
 * description :
 */
class MainViewModel : BaseViewModel() {

    val hotKeyListLiveData = MutableLiveData<List<HotKeyApi.Bean>>()

    /**
     * 在ViewModel中使用EasyHttp示例
     */
    fun getHotKey() {
        EasyHttp.get(this)
            .api(HotKeyApi::class.java)
            .request(object : OnHttpListener<HttpData<List<HotKeyApi.Bean>>> {
                override fun onHttpSuccess(result: HttpData<List<HotKeyApi.Bean>>?) {
                    hotKeyListLiveData.value = result?.getData()
                }

                override fun onHttpFail(e: Exception?) {

                }

            })
    }

}