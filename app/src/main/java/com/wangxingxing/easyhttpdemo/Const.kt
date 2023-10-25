package com.wangxingxing.easyhttpdemo

/**
 * author : 王星星
 * date : 2023/7/11 16:06
 * email : 1099420259@qq.com
 * description :
 */
object Const {

//    const val BASE_URL = "http://api.playmods.net/app/"
    // 测试
//    const val BASE_URL = "http://testapi.playmods.net/app/"
    // 预发布
//    const val BASE_URL = "https://pre-sakura.modsgamer.com/app/"
    // 正式
    const val BASE_URL = "https://sakura.modsgamer.com/app/"


    const val API_SECRET = "7dUmoccZg1DkCvbn74PnvDNxlJANWCsI"
    const val API_KEY = "SmkLzjwj87vr4s7jHBcXndXyFxDfWFus"
    const val APP_VERSION = "1.0"
    const val USER_TOKEN = ""

    const val TIME_OUT = 5

    val TEST_PACKAGE_NAME = "com.modsgamer.sakura.props"

    object SP {
        const val NAME_DEVICE_INFO = "device_info"
        const val KEY_ANDROID_ID = "android_id"
    }

    object Header {
        const val KEY_API_KEY = "apiKey" //apiKey

        const val KEY_API_SIGN = "api_sign" //api密钥

        const val KEY_USER_TOKEN = "user_token" //用户TOKEN

        const val KEY_APP_VERSION = "app_version" //api版本

        const val KEY_MAC_CODE = "mac_code" //mac地址

        const val KEY_DEVICE_NO = "device_no" //设备号

        const val KEY_CLIENT_CHANNEL_NAME = "client_channel_name" //客户端渠道号

        const val KEY_CLIENT_PACKAGE_NAME = "client_package_name" //客户端包名

        const val KEY_CLIENT_VERSION_NAME = "client_version_name" //客户端版本名

        const val KEY_CLIENT_VERSION_CODE = "client_version_code" //客户端版本号

        const val KEY_SUPPORTED_ABIS = "supported_abis" //CPU架构

        const val KEY_OS_VERSION = "os_version" //客户端系统版本

        const val KEY_LANGUAGE = "language" //客户端系统版本

        const val KEY_ANDROID_ID = "android_id" //客户端系统版本

        const val KEY_MODEL_NAME = "model_name" //手机型号

        const val KEY_IMEI = "device_imei"
        const val KEY_IMSI = "device_imsi"
        const val KEY_GAID = "gaid"
    }

    object Param {
        const val KEY_GAME_PACKAGE_NAME = "gamePackageName" //当前游戏包名
    }

    // TODO: 上线时改为 BuildConfig.DEBUG
    fun isDebug() = BuildConfig.DEBUG
}