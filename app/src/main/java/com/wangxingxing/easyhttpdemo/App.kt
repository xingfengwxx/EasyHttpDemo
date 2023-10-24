package com.wangxingxing.easyhttpdemo

import android.app.Application
import com.hjq.http.EasyConfig
import com.hjq.http.config.IRequestInterceptor
import com.hjq.http.config.IRequestServer
import com.hjq.http.model.HttpHeaders
import com.hjq.http.model.HttpParams
import com.hjq.http.request.HttpRequest
import com.hjq.toast.Toaster
import com.tencent.mmkv.MMKV
import com.wangxingxing.easyhttpdemo.http.model.RequestHandler
import com.wangxingxing.easyhttpdemo.http.server.ReleaseServer
import com.wangxingxing.easyhttpdemo.http.server.TestServer
import okhttp3.OkHttpClient

/**
 * author : 王星星
 * date : 2023/10/24 11:25
 * email : 1099420259@qq.com
 * description :
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Toaster.init(this)
        MMKV.initialize(this)

        initOkHttp()
    }

    private fun initOkHttp() {
        // 网络请求框架初始化

        val server: IRequestServer
        if (BuildConfig.DEBUG) {
            server = TestServer()
        } else {
            server = ReleaseServer()
        }

        val okHttpClient = OkHttpClient.Builder()
            .build()

        EasyConfig.with(okHttpClient)
            // 是否打印日志
            .setLogEnabled(BuildConfig.DEBUG)
            // 设置服务器配置（必须设置）
            .setServer(server)
            // 设置请求处理策略（必须设置）
            .setHandler(RequestHandler(this))
            // 设置请求参数拦截器
            .setInterceptor(object : IRequestInterceptor {
                override fun interceptArguments(
                    httpRequest: HttpRequest<*>,
                    params: HttpParams,
                    headers: HttpHeaders
                ) {
                    headers.put("timestamp", System.currentTimeMillis().toString())
                }
            })
            // 设置请求重试次数
            .setRetryCount(1)
            // 设置请求重试时间
            .setRetryTime(2000)
            // 添加全局请求参数
            .addParam("token", "6666666")
            // 添加全局请求头
            .addHeader("date", "20191030")
            .into()
    }
}