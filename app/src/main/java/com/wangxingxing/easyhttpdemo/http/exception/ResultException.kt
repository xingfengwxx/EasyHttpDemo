package com.wangxingxing.easyhttpdemo.http.exception

import com.hjq.http.exception.HttpException
import com.wangxingxing.easyhttpdemo.http.model.HttpData

/**
 * author : 王星星
 * date : 2023/10/25 11:44
 * email : 1099420259@qq.com
 * description : 返回结果异常
 */
class ResultException : HttpException {
    private val mData: HttpData<*>

    constructor(message: String?, data: HttpData<*>) : super(message) {
        mData = data
    }

    constructor(message: String?, cause: Throwable?, data: HttpData<*>) : super(message, cause) {
        mData = data
    }

    fun getHttpData(): HttpData<*> {
        return mData
    }
}