package com.meesho.assignment.ui.github_pull_list.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.meesho.assignment.AppSharedPreferences
import com.meesho.assignment.commons.data.GithubRepoPull
import com.meesho.assignment.network.ApiService
import com.meesho.assignment.network.Outcome
import com.meesho.assignment.ui.github_pull_list.model.paging.GithubPullDataSourceFactory
import io.reactivex.disposables.CompositeDisposable


class GithubRepo(
    private val appSharedPreferences: AppSharedPreferences,
    apiService: ApiService,
    private val compositeDisposable: CompositeDisposable
) : GithubDataContract.Repository {

    private val pageSize = 10
    private var githubPullListLiveData: LiveData<PagedList<GithubRepoPull>>
    private val githubPullDataSourceFactory: GithubPullDataSourceFactory =
        GithubPullDataSourceFactory(
            appSharedPreferences,
            compositeDisposable,
            apiService
        )

    private var networkStateLiveData: LiveData<Outcome<String>>

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()
        githubPullListLiveData = LivePagedListBuilder<Long, GithubRepoPull>(githubPullDataSourceFactory, config).build()

        networkStateLiveData =
            Transformations.switchMap(githubPullDataSourceFactory.getPullListDataSourceLiveData()) { dataSource ->
                dataSource.getNetworkState()
            }
    }

    override fun getGithubPullList(): LiveData<PagedList<GithubRepoPull>> = githubPullListLiveData

    override fun getRequestState(): LiveData<Outcome<String>> {
        return networkStateLiveData
    }

    override fun fetchRepoOpenPullList(repoOwner: String, repoName: String) {
        appSharedPreferences.repoOwnerId = repoOwner
        appSharedPreferences.repoName = repoName
        githubPullDataSourceFactory.getPullListDataSourceLiveData().value?.invalidate()

    }

    override fun clear() {
        compositeDisposable.dispose()
        appSharedPreferences.clear()
    }
}