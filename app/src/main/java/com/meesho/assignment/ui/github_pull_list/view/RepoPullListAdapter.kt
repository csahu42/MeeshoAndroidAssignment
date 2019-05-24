package com.meesho.assignment.ui.github_pull_list.view

import android.annotation.SuppressLint
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.meesho.assignment.R
import com.meesho.assignment.commons.data.GithubRepoPull
import com.meesho.assignment.network.Outcome
import kotlinx.android.synthetic.main.github_pull_list_item.view.*
import kotlinx.android.synthetic.main.request_state_item.view.*


class RepoPullListAdapter(private val itemClickListener: ListItemClickListener) :
    ListAdapter<GithubRepoPull, RecyclerView.ViewHolder>(DIFF_CALLBACK) {
    private var networkState: Outcome<String>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View
        return when (viewType) {
            R.layout.github_pull_list_item -> {
                view = layoutInflater.inflate(R.layout.github_pull_list_item, parent, false)
                ViewHolder(view)
            }
            R.layout.request_state_item -> {
                view = layoutInflater.inflate(R.layout.request_state_item, parent, false)
                RequestStateItemViewHolder(view)
            }
            else -> throw IllegalArgumentException("unknown view type")
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.github_pull_list_item -> (viewHolder as ViewHolder).bindView(getItem(position))
            R.layout.request_state_item -> (viewHolder as RequestStateItemViewHolder).bindView(networkState)
        }
    }

    override
    fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.request_state_item
        } else {
            R.layout.github_pull_list_item
        }
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState !== Outcome.Success(networkState)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(dataModel: GithubRepoPull) {
            itemView.title.text = dataModel.title
        }
    }


    fun setRequestState(newNetworkState: Outcome<String>) {
        val previousState = this.networkState
        val previousExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val newExtraRow = hasExtraRow()
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(itemCount)
            } else {
                notifyItemInserted(itemCount)
            }
        } else if (newExtraRow && previousState !== newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GithubRepoPull>() {
            override fun areItemsTheSame(
                oldGithubRepoPull: GithubRepoPull, newGithubRepoPull: GithubRepoPull
            ): Boolean {
                return oldGithubRepoPull.number == newGithubRepoPull.number
            }

            override fun areContentsTheSame(
                oldGithubRepoPull: GithubRepoPull, newGithubRepoPull: GithubRepoPull
            ): Boolean {
                return oldGithubRepoPull == newGithubRepoPull
            }
        }
    }

    inner class RequestStateItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(requestkState: Outcome<String>?) {
            when (requestkState) {
                is Outcome.Progress -> {
                    Log.d("GithubPull", " progress")
                    itemView.progress_bar.visibility = View.VISIBLE
                }
                is Outcome.Success -> {
                    Log.d("GithubPull", " success")
                    itemView.progress_bar.visibility = View.GONE
                    itemView.error_msg.visibility = View.GONE
                }
                is Outcome.Failure -> {
                    Log.d("GithubPull", " failure")
                    itemView.error_msg.visibility = View.VISIBLE
                    itemView.error_msg.text = requestkState.error.message
                }

            }
            itemView.retry_button.setOnClickListener { v ->
                itemClickListener.onRetryClick(v, adapterPosition)
            }
        }
    }
}