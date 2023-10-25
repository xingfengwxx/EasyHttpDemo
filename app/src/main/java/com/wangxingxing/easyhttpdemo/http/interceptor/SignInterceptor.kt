package com.wangxingxing.easyhttpdemo.http.interceptor

/**
 * author : 王星星
 * date : 2023/7/12 12:31
 * email : 1099420259@qq.com
 * description :
 */
import com.blankj.utilcode.util.EncryptUtils
import com.wangxingxing.easyhttpdemo.Const
import com.wangxingxing.easyhttpdemo.utils.LogUtils
import okhttp3.*
import java.io.IOException

// 自定义参数拦截器
class SignInterceptor : Interceptor {

    private val TAG = "SignInterceptor"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()

        // 获取动作名
        val actionName = getActionName(originalRequest)

        // 创建一个新的请求构建器，并从原始请求中复制所有内容
        val requestBuilder = originalRequest.newBuilder()

        // 获取原始请求的参数
        val originalHttpUrl = originalRequest.url()
        val urlBuilder = originalHttpUrl.newBuilder()
        val parameterNames = originalHttpUrl.queryParameterNames()
        val sb = StringBuilder()
        sb.append(Const.Header.KEY_API_KEY)
        sb.append("=")
        sb.append(Const.API_KEY)
        sb.append("&")
        // 遍历参数列表，计算签名并添加到参数中
        for (parameterName in parameterNames) {
            val parameterValue = originalHttpUrl.queryParameter(parameterName)
            sb.append(parameterName)
            sb.append("=")
            sb.append(parameterValue)
            sb.append("&")

            // 将签名添加到请求参数中
            urlBuilder.addQueryParameter(parameterName, parameterValue)
        }

        sb.append(actionName)
        sb.append("&")
        if (Const.USER_TOKEN.isNotEmpty()) {
            sb.append(Const.USER_TOKEN)
            sb.append("&")
        }
        sb.append(Const.API_SECRET)
        LogUtils.d(TAG, "sb: $sb")
        val token = EncryptUtils.encryptMD5ToString(sb.toString()).lowercase()
        LogUtils.d(TAG, "token: $token")

        // 获取原始请求的请求体
        val originalRequestBody = originalRequest.body()

        // 创建公共参数
        val commonParams = HashMap<String, String>()
        commonParams[Const.Header.KEY_API_SIGN] = token
//        commonParams["key2"] = "value2"

        // 创建新的请求体
        val newRequestBody = createNewRequestBody(originalRequestBody, commonParams)

        // 设置新的请求体
        requestBuilder.method(originalRequest.method(), newRequestBody)

        // 构建新的请求
        val newRequest = requestBuilder.build()

        // 继续处理拦截链
        return chain.proceed(newRequest)
    }

    // 获取动作名
    private fun getActionName(request: Request): String {
        // 解析请求的 URL 或请求体，提取出动作名
        var actionName = ""
        val url = request.url().toString()
        val pathSegments = url.split(Const.BASE_URL)
        if (pathSegments.size == 2) {
            actionName = pathSegments[1]
            if (actionName.contains("?")) {
                val paths = actionName.split("?")
                actionName = paths[0]
            }
        } else {
            actionName = pathSegments.last()
        }

        if (!actionName.startsWith("/")) {
            actionName = "/$actionName"
        }

        return actionName
    }

    // 创建新的请求体
    private fun createNewRequestBody(
        originalRequestBody: RequestBody?,
        commonParams: Map<String, String>,
    ): RequestBody? {
        val formBuilder = FormBody.Builder()

        // 复制原始请求体的参数
        if (originalRequestBody is FormBody) {
            for (i in 0 until originalRequestBody.size()) {
                formBuilder.add(originalRequestBody.name(i), originalRequestBody.value(i))
            }
        }

        // 添加公共参数
        for ((key, value) in commonParams) {
            formBuilder.add(key, value)
        }

        return formBuilder.build()
    }
}

