package com.wangxingxing.easyhttpdemo.http.model

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import com.google.gson.JsonSyntaxException
import com.hjq.gson.factory.GsonFactory
import com.hjq.http.EasyLog
import com.hjq.http.config.IRequestHandler
import com.hjq.http.exception.*
import com.hjq.http.request.HttpRequest
import com.wangxingxing.easyhttpdemo.R
import com.wangxingxing.easyhttpdemo.http.exception.ResultException
import com.wangxingxing.easyhttpdemo.http.exception.TokenException
import okhttp3.Headers
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.GenericArrayType
import java.lang.reflect.Type
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * author : 王星星
 * date : 2023/10/25 11:25
 * email : 1099420259@qq.com
 * description : 请求处理类
 */
class RequestHandler(application: Application) : IRequestHandler {
    private val mApplication: Application

    init {
        mApplication = application
    }

    @Throws(Exception::class)
    override fun requestSuccess(
        httpRequest: HttpRequest<*>, response: Response,
        type: Type
    ): Any {
        if (Response::class.java == type) {
            return response
        }
        if (!response.isSuccessful) {
            throw ResponseException(
                String.format(
                    mApplication.getString(R.string.http_response_error),
                    response.code, response.message
                ), response
            )
        }
        if (Headers::class.java == type) {
            return response.headers
        }
        val body = response.body
            ?: throw NullBodyException(mApplication.getString(R.string.http_response_null_body))
        if (ResponseBody::class.java == type) {
            return body
        }

        // 如果是用数组接收，判断一下是不是用 byte[] 类型进行接收的
        if (type is GenericArrayType) {
            val genericComponentType = type.genericComponentType
            if (Byte::class.javaPrimitiveType == genericComponentType) {
                return body.bytes()
            }
        }
        if (InputStream::class.java == type) {
            return body.byteStream()
        }
        if (Bitmap::class.java == type) {
            return BitmapFactory.decodeStream(body.byteStream())
        }
        val text: String = try {
            body.string()
        } catch (e: IOException) {
            // 返回结果读取异常
            throw DataException(mApplication.getString(R.string.http_data_explain_error), e)
        }

        // 打印这个 Json 或者文本
        EasyLog.printJson(httpRequest, text)
        if (String::class.java == type) {
            return text
        }
        val result: Any = try {
            GsonFactory.getSingletonGson().fromJson<Any>(text, type)
        } catch (e: JsonSyntaxException) {
            // 返回结果读取异常
            throw DataException(mApplication.getString(R.string.http_data_explain_error), e)
        }
        if (result is HttpData<*>) {
            result.setResponseHeaders(response.headers)
            if (result.isRequestSuccess()) {
                // 代表执行成功
                return result
            }
            if (result.isTokenInvalidation()) {
                // 代表登录失效，需要重新登录
                throw TokenException(mApplication.getString(R.string.http_token_error))
            }
            throw ResultException(result.getMessage(), result)
        }
        return result
    }

    override fun requestFail(httpRequest: HttpRequest<*>, e: Exception): Exception {
        if (e is HttpException) {
            if (e is TokenException) {
                // 登录信息失效，跳转到登录页
            }
            return e
        }
        if (e is SocketTimeoutException) {
            return TimeoutException(mApplication.getString(R.string.http_server_out_time), e)
        }
        if (e is UnknownHostException) {
            val info =
                (mApplication.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
            // 判断网络是否连接
            return if (info != null && info.isConnected) {
                // 有连接就是服务器的问题
                ServerException(mApplication.getString(R.string.http_server_error), e)
            } else NetworkException(mApplication.getString(R.string.http_network_error), e)
            // 没有连接就是网络异常
        }
        return if (e is IOException) {
            // 出现该异常的两种情况
            // 1. 调用 EasyHttp.cancel
            // 2. 网络请求被中断
            CancelException(mApplication.getString(R.string.http_request_cancel), e)
        } else HttpException(e.message, e)
    }

    override fun downloadFail(httpRequest: HttpRequest<*>, e: Exception): Exception {
        when (e) {
            is ResponseException -> {
                val response = e.response
                e.setMessage(
                    String.format(
                        mApplication.getString(R.string.http_response_error),
                        response.code, response.message
                    )
                )
                return e
            }
            is NullBodyException -> {
                e.setMessage(mApplication.getString(R.string.http_response_null_body))
                return e
            }
            is FileMd5Exception -> {
                e.setMessage(mApplication.getString(R.string.http_response_md5_error))
                return e
            }
            else -> return requestFail(httpRequest, e)
        }
    }

    override fun readCache(httpRequest: HttpRequest<*>, type: Type, cacheTime: Long): Any? {
        val cacheKey = HttpCacheManager.generateCacheKey(httpRequest)
        val cacheValue = HttpCacheManager.readHttpCache(cacheKey)
        if (cacheValue == null || "" == cacheValue || "{}" == cacheValue) {
            return null
        }
        EasyLog.printLog(httpRequest, "----- read cache key -----")
        EasyLog.printJson(httpRequest, cacheKey)
        EasyLog.printLog(httpRequest, "----- read cache value -----")
        EasyLog.printJson(httpRequest, cacheValue)
        EasyLog.printLog(httpRequest, "cacheTime = $cacheTime")
        val cacheInvalidate = HttpCacheManager.isCacheInvalidate(cacheKey, cacheTime)
        EasyLog.printLog(httpRequest, "cacheInvalidate = $cacheInvalidate")
        return if (cacheInvalidate) {
            // 表示缓存已经过期了，直接返回 null 给外层，表示缓存不可用
            null
        } else GsonFactory.getSingletonGson().fromJson<Any>(cacheValue, type)
    }

    override fun writeCache(httpRequest: HttpRequest<*>, response: Response, result: Any): Boolean {
        val cacheKey = HttpCacheManager.generateCacheKey(httpRequest)
        val cacheValue = GsonFactory.getSingletonGson().toJson(result)
        if (cacheValue == null || "" == cacheValue || "{}" == cacheValue) {
            return false
        }
        EasyLog.printLog(httpRequest, "----- write cache key -----")
        EasyLog.printJson(httpRequest, cacheKey)
        EasyLog.printLog(httpRequest, "----- write cache value -----")
        EasyLog.printJson(httpRequest, cacheValue)
        val writeHttpCacheResult = HttpCacheManager.writeHttpCache(cacheKey, cacheValue)
        EasyLog.printLog(httpRequest, "writeHttpCacheResult = $writeHttpCacheResult")
        val refreshHttpCacheTimeResult =
            HttpCacheManager.setHttpCacheTime(cacheKey, System.currentTimeMillis())
        EasyLog.printLog(httpRequest, "refreshHttpCacheTimeResult = $refreshHttpCacheTimeResult")
        return writeHttpCacheResult && refreshHttpCacheTimeResult
    }

    override fun clearCache() {
        HttpCacheManager.clearCache()
    }
}