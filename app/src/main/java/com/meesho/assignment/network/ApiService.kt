package com.meesho.assignment.network

import com.meesho.assignment.commons.data.GithubRepoPull
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * This interface is retrofit api Http request
 */
interface ApiService {

    @GET("repos/{repoOwner}/{repoName}/pulls")
    fun getRepoPullRequest(
        @Path("repoOwner") repoOwner: String, @Path("repoName") repoName: String,
        @Query("private") isPrivateRepo: Boolean, @Query("state") state: String,
        @Query("per_page") perPage: Int
    ): Single<List<GithubRepoPull>>


}
