package com.wangxingxing.easyhttpdemo.http.exception

import com.hjq.http.exception.HttpException

/**
 * author : 王星星
 * date : 2023/10/25 11:44
 * email : 1099420259@qq.com
 * description : 返回结果异常
 */
class TokenException : HttpException {
    constructor(message: String?) : super(message) {}
    constructor(message: String?, cause: Throwable?) : super(message, cause) {}
}