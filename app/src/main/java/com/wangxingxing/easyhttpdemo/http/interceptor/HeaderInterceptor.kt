package com.wangxingxing.easyhttpdemo.http.interceptor

import android.os.Build
import android.text.TextUtils
import com.blankj.utilcode.util.*
import com.wangxingxing.easyhttpdemo.Const
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.*

import com.wangxingxing.easyhttpdemo.utils.LogUtils

/**
 * author : 王星星
 * date : 2023/7/11 16:16
 * email : 1099420259@qq.com
 * description :
 */
class HeaderInterceptor : Interceptor {

    private val TAG = "HeaderInterceptor"

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder().apply {
            header(Const.Header.KEY_ANDROID_ID, DeviceUtils.getUniqueDeviceId())
            header(Const.Header.KEY_LANGUAGE, LanguageUtils.getSystemLanguage().language)
            header(Const.Header.KEY_OS_VERSION, DeviceUtils.getSDKVersionName())
            header(Const.Header.KEY_APP_VERSION, Const.APP_VERSION)
            header(Const.Header.KEY_CLIENT_VERSION_NAME, AppUtils.getAppVersionName())
            header(Const.Header.KEY_CLIENT_VERSION_CODE, AppUtils.getAppVersionCode().toString())
            header(Const.Header.KEY_CLIENT_PACKAGE_NAME, Const.TEST_PACKAGE_NAME)
            header(Const.Header.KEY_MODEL_NAME, DeviceUtils.getModel())
            header(Const.Header.KEY_CLIENT_CHANNEL_NAME, getChannel())
            header(Const.Header.KEY_SUPPORTED_ABIS, ArrayUtils.toString(DeviceUtils.getABIs()))
            header(Const.Header.KEY_USER_TOKEN, "")
            header(Const.Header.KEY_MAC_CODE, "")
            header(Const.Header.KEY_DEVICE_NO, "${DeviceUtils.getManufacturer()}+${DeviceUtils.getModel()}")
            header(Const.Header.KEY_IMEI, "")
            header(Const.Header.KEY_IMSI, "")
            header(Const.Header.KEY_API_SIGN, getSign(original))
        }.build()
        return chain.proceed(request)
    }

    private fun getSign(originalRequest: Request): String {
        // 获取动作名
        val actionName = getActionName(originalRequest)

        // 获取原始请求的参数
        val originalHttpUrl = originalRequest.url
        val parameterNames = originalHttpUrl.queryParameterNames
        val sortedParameterNames = parameterNames.sorted()

        val signStr = StringBuilder()
        signStr.append(Const.Header.KEY_API_KEY)
        signStr.append("=")
        signStr.append(Const.API_KEY)
        signStr.append("&")
        // 遍历参数列表，计算签名并添加到参数中
        for (parameterName in sortedParameterNames) {
            val parameterValue = originalHttpUrl.queryParameter(parameterName)
            signStr.append(parameterName)
            signStr.append("=")
            signStr.append(parameterValue)
            signStr.append("&")
        }
        signStr.append(actionName)
        signStr.append("&")
        if (Const.USER_TOKEN.isNotEmpty()) {
            signStr.append(Const.USER_TOKEN)
            signStr.append("&")
        }
        signStr.append(Const.API_SECRET)
        LogUtils.d(TAG, "signStr: $signStr")
        val signMd5 = EncryptUtils.encryptMD5ToString(signStr.toString()).lowercase()
        LogUtils.d(TAG, "sign md5: $signMd5")
        return signMd5
    }

    // 获取动作名
    private fun getActionName(request: Request): String {
        // 解析请求的 URL 或请求体，提取出动作名
        var actionName = ""
        val url = request.url.toString()
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

    private fun getAndroidId(): String {
        var androidId = SPUtils.getInstance(Const.SP.NAME_DEVICE_INFO).getString(Const.SP.KEY_ANDROID_ID)
        if (androidId.isEmpty()) {
            androidId = UUID.randomUUID().toString()
            SPUtils.getInstance(Const.SP.NAME_DEVICE_INFO).put(Const.SP.KEY_ANDROID_ID, androidId)
        }
        return androidId
    }

    private fun getChannel() = "google"

    private fun getSupportedABIS(): String {
        var supportedABIS = ""
        if (Build.SUPPORTED_ABIS != null) {
            supportedABIS = TextUtils.join("", Build.SUPPORTED_ABIS)
        }
        return supportedABIS
    }
}