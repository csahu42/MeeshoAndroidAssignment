package com.meesho.assignment.commons.utils

import com.meesho.assignment.BuildConfig


object Constants {
    object AppUrl {
        // const val APP_BASE_URL = "https://jsonplaceholder.typicode.com/"
        const val APP_BASE_URL = " https://api.github.com/"

    }

    object FileProvider {
        const val AUTHORITY = BuildConfig.APPLICATION_ID + ".provider"
    }

    object AppSharedPreferences {
        const val BASE_URL: String = "baseUrl"
        const val OWNER_NAME: String = "owner_name"
        const val REPO_NAME: String = "repo_name"
    }
}