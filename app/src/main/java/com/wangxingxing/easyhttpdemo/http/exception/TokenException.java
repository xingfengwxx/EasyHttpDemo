package com.wangxingxing.easyhttpdemo.http.exception;

import com.hjq.http.exception.HttpException;

/**
 * author : 王星星
 * date : 2023/10/24 17:56
 * email : 1099420259@qq.com
 * description : Token 失效异常
 */
public final class TokenException extends HttpException {

    public TokenException(String message) {
        super(message);
    }

    public TokenException(String message, Throwable cause) {
        super(message, cause);
    }
}