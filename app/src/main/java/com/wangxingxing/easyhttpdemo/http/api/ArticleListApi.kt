package com.wangxingxing.easyhttpdemo.http.api

import com.hjq.http.annotation.HttpIgnore
import com.hjq.http.config.IRequestApi

/**
 * author : 王星星
 * date : 2023/10/24 19:47
 * email : 1099420259@qq.com
 * description :
 */
class ArticleListApi : IRequestApi {

    override fun getApi(): String {
        return "article/list/$pageNumber/json"
    }

    @HttpIgnore
    private var pageNumber: Int = 0

    fun setPageNumber(pageNumber: Int): ArticleListApi {
        this.pageNumber = pageNumber
        return this
    }

    class Bean {

    }

}