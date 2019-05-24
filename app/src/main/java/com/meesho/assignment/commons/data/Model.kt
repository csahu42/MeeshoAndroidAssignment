package com.meesho.assignment.commons.data

import com.google.gson.annotations.SerializedName

data class GithubRepoPull(
    @SerializedName("number") val number: Int,
    @SerializedName("title") val title: String,
    @SerializedName("message") var message: String?
)