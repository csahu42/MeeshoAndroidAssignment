package com.meesho.assignment.ui.github_pull_list.model.paging


import android.arch.lifecycle.MutableLiveData
import android.arch.paging.ItemKeyedDataSource
import com.meesho.assignment.AppSharedPreferences
import com.meesho.assignment.commons.data.GithubRepoPull
import com.meesho.assignment.network.ApiService
import com.meesho.assignment.network.Outcome
import io.reactivex.disposables.CompositeDisposable


class GithubPullDataSource(
    private val appSharedPreferences: AppSharedPreferences,
    private val apiService: ApiService,
    private val compositeDisposable: CompositeDisposable
) : ItemKeyedDataSource<Long, GithubRepoPull>() {
    private val networkState: MutableLiveData<Outcome<String>> = MutableLiveData()
    private var initialLoading: MutableLiveData<Outcome<String>> = MutableLiveData()

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<GithubRepoPull>) {
        networkState.postValue(Outcome.loading(true))
        initialLoading.postValue(Outcome.loading(true))
        compositeDisposable.add(
            apiService.getRepoPullRequest(
                appSharedPreferences.repoOwnerId,
                appSharedPreferences.repoName,
                false,
                "open",
                params.requestedLoadSize
            )
                .subscribe({ response ->
                    response?.let { callback.onResult(it) }
                    initialLoading.postValue(Outcome.Success("SUCCESS"))
                    networkState.postValue(Outcome.success("SUCCESS"))
                }, { error -> networkState.postValue(Outcome.failure(error)) })
        )
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<GithubRepoPull>) {
        networkState.postValue(Outcome.loading(true))
        compositeDisposable.add(
            apiService.getRepoPullRequest(
                appSharedPreferences.repoOwnerId,
                appSharedPreferences.repoName,
                false,
                "open",
                params.requestedLoadSize
            ).subscribe({ response ->
                networkState.postValue(Outcome.success("SUCCESS"))
                response?.let { callback.onResult(it) }
            }, { error -> networkState.postValue(Outcome.failure(error)) })
        )
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<GithubRepoPull>) {

    }


    override fun getKey(item: GithubRepoPull): Long {
        return item.number.toLong()
    }

    fun getNetworkState(): MutableLiveData<Outcome<String>> = networkState
}
