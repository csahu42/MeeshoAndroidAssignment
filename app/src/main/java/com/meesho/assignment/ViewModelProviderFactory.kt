package com.meesho.assignment

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.meesho.assignment.ui.github_pull_list.model.GithubDataContract
import com.meesho.assignment.ui.github_pull_list.viewmodel.MainViewModel


/**
 * ViewModel Provider
 */
class ViewModelProviderFactory(
    private val githubRepo: GithubDataContract.Repository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java))
            return MainViewModel(githubRepo) as T
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)

    }
}