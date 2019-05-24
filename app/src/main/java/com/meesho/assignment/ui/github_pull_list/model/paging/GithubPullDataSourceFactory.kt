package com.meesho.assignment.ui.github_pull_list.model.paging

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.meesho.assignment.AppSharedPreferences
import com.meesho.assignment.commons.data.GithubRepoPull
import com.meesho.assignment.network.ApiService
import io.reactivex.disposables.CompositeDisposable


class GithubPullDataSourceFactory(
    private val appSharedPreferences: AppSharedPreferences,
    private val compositeDisposable: CompositeDisposable,
    private val apiService: ApiService
) : DataSource.Factory<Long, GithubRepoPull>() {

    private val pullListDataSourceLiveData = MutableLiveData<GithubPullDataSource>()
    override fun create(): DataSource<Long, GithubRepoPull> {
        val dataSource = GithubPullDataSource(
            appSharedPreferences,
            apiService,
            compositeDisposable
        )
        pullListDataSourceLiveData.postValue(dataSource)
        return dataSource
    }

    fun getPullListDataSourceLiveData(): MutableLiveData<GithubPullDataSource> = pullListDataSourceLiveData
}