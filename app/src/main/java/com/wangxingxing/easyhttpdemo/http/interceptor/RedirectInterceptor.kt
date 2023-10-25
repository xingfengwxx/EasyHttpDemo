package com.wangxingxing.easyhttpdemo.http.interceptor

import com.wangxingxing.easyhttpdemo.utils.LogUtils
import okhttp3.Interceptor
import okhttp3.Response

/**
 * author : 王星星
 * date : 2023/7/20 20:04
 * email : 1099420259@qq.com
 * description : 重定向拦截器
 */

private const val TAG = "RedirectInterceptor"

class RedirectInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        // Check if response is a redirect (status code 3xx)
        if (response.isRedirect) {
            val location = response.header("Location")
            // Handle the 'Location' header here
            LogUtils.d(TAG, "Redirect Location: $location")
            location?.let {
//                getRealDownloadUrl(it)
            }
        }

        return response
    }
}