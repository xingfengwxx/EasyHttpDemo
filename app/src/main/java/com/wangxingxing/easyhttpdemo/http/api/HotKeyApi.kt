package com.wangxingxing.easyhttpdemo.http.api

import com.hjq.http.config.IRequestApi

/**
 * author : 王星星
 * date : 2023/10/25 12:11
 * email : 1099420259@qq.com
 * description :
 */
class HotKeyApi : IRequestApi {

    override fun getApi(): String = "/hotkey/json"

    class Bean {
        val id = 0
        val link: String? = null
        val name: String? = null
        val order = 0
        val visible = 0
    }
}