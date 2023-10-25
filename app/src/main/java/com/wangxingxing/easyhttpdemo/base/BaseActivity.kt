package com.wangxingxing.easyhttpdemo.base

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import com.hjq.http.listener.OnHttpListener
import com.hjq.toast.Toaster
import com.wangxingxing.easyhttpdemo.R
import okhttp3.Call

/**
 * author : 王星星
 * date : 2023/10/24 14:54
 * email : 1099420259@qq.com
 * description :
 */
open class BaseActivity : AppCompatActivity(), OnHttpListener<Any?> {
    /** 加载对话框  */
    private var mDialog: ProgressDialog? = null

    /** 对话框数量  */
    private var mDialogTotal = 0

    /**
     * 当前加载对话框是否在显示中
     */
    fun isShowDialog(): Boolean {
        return mDialog != null && mDialog!!.isShowing
    }

    /**
     * 显示加载对话框
     */
    fun showDialog() {
        if (mDialog == null) {
            mDialog = ProgressDialog(this)
            mDialog!!.setMessage(resources.getString(R.string.dialog_loading_hint))
            mDialog!!.setCancelable(false)
            mDialog!!.setCanceledOnTouchOutside(false)
        }
        if (!mDialog!!.isShowing) {
            mDialog!!.show()
        }
        mDialogTotal++
    }

    /**
     * 隐藏加载对话框
     */
    fun hideDialog() {
        if (mDialogTotal == 1) {
            if (mDialog != null && mDialog!!.isShowing) {
                mDialog!!.dismiss()
            }
        }
        if (mDialogTotal > 0) {
            mDialogTotal--
        }
    }

    override fun onHttpStart(call: Call?) {
        showDialog()
    }

    override fun onHttpSuccess(result: Any?) {}
    override fun onHttpFail(e: Exception) {
        Toaster.show(e.message)
    }

    override fun onHttpEnd(call: Call?) {
        hideDialog()
    }
}