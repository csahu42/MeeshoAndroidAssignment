package com.meesho.assignment

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import com.meesho.assignment.commons.utils.Constants

import javax.inject.Inject
import javax.inject.Named


/**
 * App SharedPreference for storing key-value into the sSharedPreference
 */

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AppSharedPreferences
@Inject constructor(@Named("app") appContext: Context) {
    private val appName: String = appContext.packageName
    private val preferences: SharedPreferences = appContext.getSharedPreferences(appName, Context.MODE_PRIVATE)


    var repoOwnerId: String = ""
        get() {
            if (TextUtils.isEmpty(field)) {
                field = this.preferences.getString(Constants.AppSharedPreferences.OWNER_NAME, "+91")
            }
            return field
        }
        set(value) {
            field = value
            this.preferences.edit().putString(Constants.AppSharedPreferences.OWNER_NAME, field).apply()
        }

    var repoName: String = ""
        get() {
            if (TextUtils.isEmpty(field)) {
                field = this.preferences.getString(Constants.AppSharedPreferences.REPO_NAME, "")
            }
            return field
        }
        set(value) {
            field = value
            this.preferences.edit().putString(Constants.AppSharedPreferences.REPO_NAME, field).apply()
        }

    fun clear() {
        preferences.edit().clear().apply()
    }
}