package com.wangxingxing.easyhttpdemo

import android.os.Bundle
import android.util.Log
import com.hjq.http.EasyHttp
import com.hjq.http.listener.HttpCallbackProxy
import com.wangxingxing.easyhttpdemo.databinding.ActivityMainBinding
import com.wangxingxing.easyhttpdemo.http.api.BannerApi
import com.wangxingxing.easyhttpdemo.http.model.HttpData


class MainActivity : BaseActivity() {

    private val TAG = "wxx"

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnRequest.setOnClickListener {
            getBannerData()
        }
    }

    private fun getBannerData() {
        EasyHttp.get(this)
            .api(BannerApi::class.java)
            .request(object : HttpCallbackProxy<HttpData<List<BannerApi.Bean>>>(this) {
                override fun onHttpSuccess(result: HttpData<List<BannerApi.Bean>>?) {
                    Log.i(TAG, "onHttpSuccess: 获取banner数据成功：index=0, desc: " + result?.getData()?.get(0)?.desc)
                }
            })
//        EasyHttp.get(this)
//            .api(SearchAuthorApi().setId(190000))
//            .request(object : HttpCallbackProxy<HttpData<List<SearchAuthorApi.Bean>>>(this) {
//                override fun onHttpSuccess(result: HttpData<List<SearchAuthorApi.Bean>>?) {
//                    Log.i(TAG, "onHttpSuccess: Get请求成功")
//                }
//            })
    }
}