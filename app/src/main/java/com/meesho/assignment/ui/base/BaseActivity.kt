package com.meesho.assignment.ui.base

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import com.meesho.assignment.commons.utils.NetworkUtil

abstract class BaseActivity : AppCompatActivity() {

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun isNetworkConnected(): Boolean {
        return NetworkUtil.isNetworkConnected(applicationContext)
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }
}