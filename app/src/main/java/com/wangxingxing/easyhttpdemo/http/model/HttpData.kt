package com.wangxingxing.easyhttpdemo.http.model

import okhttp3.Headers

/**
 * author : 王星星
 * date : 2023/10/25 11:47
 * email : 1099420259@qq.com
 * description :
 */
open class HttpData<T> {
    /** 响应头  */
    private var responseHeaders: Headers? = null

    /** 返回码  */
    private val errorCode = 0

    /** 提示语  */
    private val errorMsg: String? = null

    /** 数据  */
    private val data: T? = null
    fun setResponseHeaders(responseHeaders: Headers?) {
        this.responseHeaders = responseHeaders
    }

    fun getResponseHeaders(): Headers? {
        return responseHeaders
    }

    fun getCode(): Int {
        return errorCode
    }

    fun getMessage(): String? {
        return errorMsg
    }

    fun getData(): T? {
        return data
    }

    /**
     * 是否请求成功
     */
    fun isRequestSuccess(): Boolean {
        // 这里为了兼容 WanAndroid 接口才这样写，但是一般情况下不建议这么设计
        // 因为 int 的默认值就是 0，这样就会导致，后台返回结果码为 0 和没有返回的效果是一样的
        // 本质上其实不一样，没有返回结果码本身就是一种错误数据结构，理论上应该走失败的回调
        // 因为这里会判断是否等于 0，所以就会导致原本走失败的回调，结果走了成功的回调
        // 所以在定义错误码协议的时候，请不要将后台返回的某个成功码或者失败码的值设计成 0
        // 如果你的项目已经出现了这种情况，可以尝试将结果码的数据类型从 int 修改成 Integer
        // 这样就可以通过结果码是否等于 null 来判断后台是否返回了，当然这样也有一些弊端
        // 后面外层在使用这个结果码的时候，要先对 Integer 对象进行一次判空，否则会出现空指针异常
        return errorCode == 0
    }

    /**
     * 是否 Token 失效
     */
    fun isTokenInvalidation(): Boolean {
        return errorCode == 1001
    }
}