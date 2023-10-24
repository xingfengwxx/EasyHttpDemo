package com.wangxingxing.easyhttpdemo.http.api

import com.hjq.http.config.IRequestApi

/**
 * author : 王星星
 * date : 2023/10/24 14:43
 * email : 1099420259@qq.com
 * description : 获取banner数据
 */
class BannerApi : IRequestApi {

    override fun getApi(): String = "banner/json"

    class Bean {
        val desc: String? = null
        val id = 0
        val imagePath: String? = null
        val isVisible = 0
        val order = 0
        val title: String? = null
        val type = 0
        val url: String? = null
    }

}