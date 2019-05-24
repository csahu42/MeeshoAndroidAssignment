package com.meesho.assignment.di.module

import com.meesho.assignment.AppSharedPreferences
import com.meesho.assignment.ViewModelProviderFactory
import com.meesho.assignment.network.ApiService
import com.meesho.assignment.ui.github_pull_list.model.GithubDataContract
import com.meesho.assignment.ui.github_pull_list.model.GithubRepo
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable


/**
 * Activity level scope for the objects provider
 */
@Module
class GithubPullListActivityModule {

    /*ViewModelProviderFactory*/
    @Provides
    fun getViewModelProviderFactory(
        githubRepo: GithubDataContract.Repository
    ): ViewModelProviderFactory =
        ViewModelProviderFactory(githubRepo)

    //Repository
    @Provides
    fun getGithubRepo(
        appSharedPreferences: AppSharedPreferences,
        apiService: ApiService,
        compositeDisposable: CompositeDisposable
    ): GithubDataContract.Repository = GithubRepo(appSharedPreferences, apiService, compositeDisposable)

    /*rx task handler*/
    @Provides
    fun compositeDisposable(): CompositeDisposable = CompositeDisposable()
}
