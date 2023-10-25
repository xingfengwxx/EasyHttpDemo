package com.wangxingxing.easyhttpdemo.http.api

import com.hjq.http.EasyConfig
import com.hjq.http.config.IRequestApi
import com.hjq.http.config.IRequestClient
import com.hjq.http.config.IRequestServer
import com.wangxingxing.easyhttpdemo.Const
import com.wangxingxing.easyhttpdemo.http.interceptor.BasicParamsInterceptor
import com.wangxingxing.easyhttpdemo.http.interceptor.HeaderInterceptor
import com.wangxingxing.easyhttpdemo.http.interceptor.RedirectInterceptor
import com.wangxingxing.easyhttpdemo.utils.LogUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit


/**
 * author : 王星星
 * date : 2023/10/25 15:01
 * email : 1099420259@qq.com
 * description :
 */
class ConfigApi : IRequestServer, IRequestApi, IRequestClient {

    override fun getHost(): String = Const.BASE_URL

    override fun getApi(): String = "tool/melon/getConfig"

    /**
     * 局部配置只对这个接口生效
     * @return OkHttpClient
     */
    override fun getOkHttpClient(): OkHttpClient {
        val builder: OkHttpClient.Builder = EasyConfig.getInstance().client.newBuilder()
        builder
            // 关闭自动重定向
            .followRedirects(false)
            .addInterceptor(RedirectInterceptor())
            // 这里需要注意拦截器的顺序
            .addInterceptor(BasicParamsInterceptor())
            .addInterceptor(HeaderInterceptor())
            .addNetworkInterceptor(getHttpLoggingInterceptor())
            .connectTimeout(Const.TIME_OUT.toLong(), TimeUnit.SECONDS)
        return builder.build()
    }

    private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            LogUtils.w("OkHttp", it)
        })
        if (Const.isDebug()) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.BASIC
        }
        return logging
    }

    class Bean {
        var associationList: kotlin.collections.List<AssociationList>? = null
        var autoCopyStatus: Int? = null
        var autoCopyText: String? = null
        var contentCategoryList: kotlin.collections.List<ContentCategoryList>? = null
        var contentMenuName: String? = null
        var contentSource: Int? = null
        var contentTotal: Int? = null
        var copyBtnFlag: Int? = null
        var createTime: Long? = null
        var currentCountry: String? = null
        var downloadUrl: String? = null
        var gameName: String? = null
        var gamePackageName: String? = null
        var id: Int? = null
        var list: kotlin.collections.List<List>? = null
        var lowVersionCode: Int? = null
        var menuOrdering: Int? = null
        var modSource: Int? = null
        var name: String? = null
        var packageNames: String? = null
        var pmPropagandaStatus: Int? = null
        var pmPropagandaText: String? = null
        var privacy: String? = null
        var privacyText: Any? = null
        var rateUs: String? = null
        var shareContent: String? = null
        var sourceType: Int? = null
        var total: Int? = null
        var updateTime: Long? = null
        var usingHelp: String? = null
        var usingHelpText: Any? = null

        class AssociationList {
            var icon: String? = null
            var id: Int? = null
            var name: String? = null
            var url: String? = null
        }

        class ContentCategoryList {
            var id: Int? = null
            var name: String? = null
        }

        class List {
            var id: Int? = null
            var name: String? = null
        }
    }
}