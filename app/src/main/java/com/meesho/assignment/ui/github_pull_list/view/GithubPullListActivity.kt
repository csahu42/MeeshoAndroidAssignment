package com.meesho.assignment.ui.github_pull_list.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.meesho.assignment.R
import com.meesho.assignment.ViewModelProviderFactory
import com.meesho.assignment.network.Outcome
import com.meesho.assignment.ui.base.BaseActivity
import com.meesho.assignment.ui.github_pull_list.viewmodel.MainViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_github_pull_list.*
import javax.inject.Inject

/**
 * this Main Activity class for showing the list of public github open pull list
 */

class GithubPullListActivity : BaseActivity(), View.OnClickListener, ListItemClickListener {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, viewModelProviderFactory).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_github_pull_list)
        val repoPullListAdapter = RepoPullListAdapter(this)
        repoPullList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        repoPullList.adapter = repoPullListAdapter
        repoPullList.addItemDecoration(
            DividerItemDecoration(
                repoPullList.context,
                (repoPullList.layoutManager as LinearLayoutManager).orientation
            )
        )
        //observing for the data from viewmodel
        viewModel.getGithubPullList().observe(this, Observer { repoPullList ->
            if (repoPullList!!.isEmpty())
                message.visibility = View.VISIBLE
            else message.visibility = View.GONE
            repoPullListAdapter.submitList(repoPullList)
        })
        //observing the client request to the server
        viewModel.getRequestState().observe(this, Observer { outCome ->
            outCome?.let {
                when (outCome) {
                    is Outcome.Failure -> {
                        if (outCome.error.message!!.contains("404"))
                            showSnackBar("Invalid Owner Id or Repo Name")
                        else showSnackBar(outCome.error.message!!)
                    }
                }
                repoPullListAdapter.setRequestState(it)
            }
        })
    }

    //button click function
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnSearch -> {
                val ownerId = repoOwner.text.toString().trim()
                val repoName = repoName.text.toString().trim()
                if (ownerId.isNotEmpty() && repoName.isNotEmpty()) {
                    //checking network connectivity
                    if (isNetworkConnected())
                        fetchPullList(ownerId, repoName)
                    else showSnackBar(getString(R.string.no_internet))
                } else {
                    showSnackBar("all the fields are mandatory")
                }

            }
        }
    }

    //shows the message
    private fun showSnackBar(message: String) {
        Snackbar.make(repoPullList.rootView, message, Snackbar.LENGTH_LONG).show()
    }

    private fun fetchPullList(ownerId: String, repoName: String) {
        viewModel.fetchRepoOpenPullList(ownerId, repoName)
    }

    override fun onRetryClick(view: View, position: Int) {

    }
}
