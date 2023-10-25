package com.wangxingxing.easyhttpdemo.http.interceptor

import com.wangxingxing.easyhttpdemo.Const
import okhttp3.Interceptor
import okhttp3.Response

/**
 * author : 王星星
 * date : 2023/7/28 15:14
 * email : 1099420259@qq.com
 * description : 公用参数拦截器
 */
class BasicParamsInterceptor : Interceptor {



    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url()
        val url = originalHttpUrl.newBuilder().apply {
            // TODO: 使用甜瓜工具包名测试，上线时替换
//            addQueryParameter(Const.Param.KEY_GAME_PACKAGE_NAME, AppUtils.getAppPackageName())
            addQueryParameter(Const.Param.KEY_GAME_PACKAGE_NAME, Const.TEST_PACKAGE_NAME)
        }.build()
        val request = originalRequest.newBuilder().url(url)
            .method(originalRequest.method(), originalRequest.body()).build()
        return chain.proceed(request)
    }
}