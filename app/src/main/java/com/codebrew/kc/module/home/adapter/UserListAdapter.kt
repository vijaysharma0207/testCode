package com.codebrew.kc.module.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codebrew.kc.databinding.ItemUserListBinding
import com.codebrew.kc.retrofit.model.UserListResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class UserListAdapter() :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(MessageListDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun submitMessageList(list: List<UserListResponse>?) {
        adapterScope.launch {
            val items = list?.map { DataItem.MessageItem(it) }

            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val nightItem = getItem(position) as DataItem.MessageItem
                holder.bind(nightItem.messageData)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return  ViewHolder.from(parent)
        }
    }


    class ViewHolder private constructor(private val binding: ItemUserListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UserListResponse) {
            item.pos=adapterPosition
            binding.userData = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemUserListBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }


class MessageListDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.chatData == newItem.chatData
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}


sealed class DataItem {
    data class MessageItem(val messageData: UserListResponse) : DataItem() {
        override val chatData = messageData
    }

    abstract val chatData: UserListResponse
}

