package com.meesho.assignment.ui.github_pull_list.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.meesho.assignment.commons.data.GithubRepoPull
import com.meesho.assignment.network.Outcome
import com.meesho.assignment.ui.github_pull_list.model.GithubDataContract
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(
    private val githubRepo: GithubDataContract.Repository
) : ViewModel() {

    fun getGithubPullList(): LiveData<PagedList<GithubRepoPull>> = githubRepo.getGithubPullList()
    fun getRequestState(): LiveData<Outcome<String>> = githubRepo.getRequestState()

    fun fetchRepoOpenPullList(repoOwner: String, repoName: String) {
        githubRepo.fetchRepoOpenPullList(repoOwner, repoName)
    }

    override fun onCleared() {
        githubRepo.clear()
        super.onCleared()
    }
}