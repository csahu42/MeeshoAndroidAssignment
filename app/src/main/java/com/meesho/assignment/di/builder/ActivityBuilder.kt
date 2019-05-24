package com.meesho.assignment.di.builder

import com.meesho.assignment.di.module.GithubPullListActivityModule
import com.meesho.assignment.ui.github_pull_list.view.GithubPullListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [GithubPullListActivityModule::class])
    internal abstract fun bindMainActivity(): GithubPullListActivity
}