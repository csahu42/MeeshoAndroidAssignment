package com.meesho.assignment.ui.github_pull_list.model

import android.arch.lifecycle.LiveData
import android.arch.paging.PagedList
import com.meesho.assignment.commons.data.GithubRepoPull
import com.meesho.assignment.network.Outcome


interface GithubDataContract {
    interface Repository {
        fun getGithubPullList(): LiveData<PagedList<GithubRepoPull>>
        fun fetchRepoOpenPullList(repoOwner: String, repoName: String)
        fun getRequestState(): LiveData<Outcome<String>>
        fun clear()
    }
}