package com.wangxingxing.easyhttpdemo.http.server

import com.hjq.http.config.IRequestServer

/**
 * author : 王星星
 * date : 2023/10/24 18:03
 * email : 1099420259@qq.com
 * description :
 */
open class ReleaseServer : IRequestServer {
    override fun getHost(): String = "https://www.wanandroid.com/"
}