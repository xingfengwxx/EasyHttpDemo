package com.wangxingxing.easyhttpdemo.http.exception;

import androidx.annotation.NonNull;

import com.hjq.http.exception.HttpException;
import com.wangxingxing.easyhttpdemo.http.model.HttpData;

/**
 * author : 王星星
 * date : 2023/10/24 17:56
 * email : 1099420259@qq.com
 * description : 返回结果异常
 */
public final class ResultException extends HttpException {

    private final HttpData<?> mData;

    public ResultException(String message, HttpData<?> data) {
        super(message);
        mData = data;
    }

    public ResultException(String message, Throwable cause, HttpData<?> data) {
        super(message, cause);
        mData = data;
    }

    @NonNull
    public HttpData<?> getHttpData() {
        return mData;
    }
}