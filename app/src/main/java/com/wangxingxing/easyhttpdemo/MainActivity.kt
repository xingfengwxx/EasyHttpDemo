package com.wangxingxing.easyhttpdemo

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.hjq.http.EasyHttp
import com.hjq.http.listener.HttpCallbackProxy
import com.wangxingxing.easyhttpdemo.base.BaseActivity
import com.wangxingxing.easyhttpdemo.databinding.ActivityMainBinding
import com.wangxingxing.easyhttpdemo.http.api.BannerApi
import com.wangxingxing.easyhttpdemo.http.api.ConfigApi
import com.wangxingxing.easyhttpdemo.http.model.HttpData
import com.wangxingxing.easyhttpdemo.utils.LogUtils


class MainActivity : BaseActivity() {

    private val TAG = "wxx"

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initObserver()
    }

    private fun initView() {
        binding.btnRequest.setOnClickListener {
            getBannerData()
        }

        binding.btnRequestInViewModel.setOnClickListener {
            viewModel.getHotKey()
        }

        binding.btnRequestAddInterceptor.setOnClickListener {
            getConfig()
        }
    }

    private fun initObserver() {
        viewModel.hotKeyListLiveData.observe(this) {
            Log.i(TAG, "initObserver: hot key list size=${it.size}")
        }
    }

    private fun getBannerData() {

//        EasyHttp.get(this)
//            .api(ArticleListApi().setPageNumber(1))
//            .request(object : HttpCallbackProxy<HttpData<List<ArticleListApi.Bean>>>(this) {
//                override fun onHttpSuccess(result: HttpData<List<ArticleListApi.Bean>>?) {
//                    Log.i(TAG, "onHttpSuccess: 请求文章列表成功")
//                }
//            })
        
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

    private fun getConfig() {
        EasyHttp.post(this)
            .api(ConfigApi::class.java)
            .request(object : HttpCallbackProxy<HttpData<ConfigApi.Bean>>(this) {
                override fun onHttpSuccess(result: HttpData<ConfigApi.Bean>?) {
                    LogUtils.i(TAG, "请求接口成功：${result?.getData()?.shareContent}")
                }
            })
    }


}