package com.wangxingxing.easyhttpdemo.http.model

/**
 * author : 王星星
 * date : 2023/10/25 11:48
 * email : 1099420259@qq.com
 * description : 统一接口列表数据结构
 */
class HttpListData<T> : HttpData<HttpListData.ListBean<T?>?>() {
    class ListBean<T> {
        /** 当前页码  */
        val pageIndex = 0

        /** 页大小  */
        val pageSize = 0

        /** 总数量  */
        val totalNumber = 0

        /** 数据  */
        val items: List<T>? = null

        /**
         * 判断是否是最后一页
         */
        val isLastPage: Boolean
            get() = Math.ceil((totalNumber.toFloat() / pageSize).toDouble()) <= pageIndex
    }
}