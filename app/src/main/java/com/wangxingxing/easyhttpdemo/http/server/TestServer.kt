package com.wangxingxing.easyhttpdemo.http.server

import com.hjq.http.config.IRequestBodyStrategy
import com.hjq.http.model.RequestBodyType

/**
 * author : 王星星
 * date : 2023/10/24 17:53
 * email : 1099420259@qq.com
 * description : 测试环境
 */
class TestServer : ReleaseServer() {

    override fun getHost(): String = "https://www.wanandroid.com/"

    // 参数以 Json 格式提交（默认是表单）
//    override fun getBodyType(): IRequestBodyStrategy = RequestBodyType.JSON
}