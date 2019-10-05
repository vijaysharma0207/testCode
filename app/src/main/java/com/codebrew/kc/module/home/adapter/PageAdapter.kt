package com.codebrew.kc.module.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codebrew.kc.databinding.FooterCountTextBinding
import com.codebrew.kc.databinding.ItemCountTextBinding
import com.codebrew.kc.retrofit.model.custom.PageStatusModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private const val ITEM_VIEW_TYPE_ITEM = 1
private const val ITEM_VIEW_TYPE_FOOTER = 2

class PageAdapter(private val clickListener: PageListener):
        ListAdapter<PageDataItem, RecyclerView.ViewHolder>(SleepNightDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addFooterAndSubmitList(list: List<PageStatusModel>?) {
        adapterScope.launch {
            val items = when (list) {
                null ->  listOf(PageDataItem.Footer)
                else ->  list.map { PageDataItem.SleepNightItem(it) }+ listOf(PageDataItem.Footer)
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val nightItem = getItem(position) as PageDataItem.SleepNightItem
                holder.bind(clickListener,nightItem.address)
            }

            is TextViewFooter ->{
                holder.bind(clickListener)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_FOOTER -> TextViewFooter.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PageDataItem.Footer -> ITEM_VIEW_TYPE_FOOTER
            is PageDataItem.SleepNightItem -> ITEM_VIEW_TYPE_ITEM
        }
    }




    class TextViewFooter private constructor(val binding: FooterCountTextBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: PageListener) {
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): TextViewFooter {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FooterCountTextBinding.inflate(layoutInflater, parent, false)
                return TextViewFooter(binding)
            }
        }
    }



    class ViewHolder private constructor(val binding: ItemCountTextBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(clickListener: PageListener,item: PageStatusModel) {
            binding.countData = item
            binding.clickListener=clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCountTextBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class PageListener(val itemListener: (item: PageStatusModel) -> Unit,val footerlistener:(mType:String)->Unit) {

    fun onClick(item: PageStatusModel) = itemListener(item)

    fun onFooterClick(mType: String)=footerlistener(mType)

}

class SleepNightDiffCallback : DiffUtil.ItemCallback<PageDataItem>() {
    override fun areItemsTheSame(oldItem: PageDataItem, newItem: PageDataItem): Boolean {
        return oldItem.address == newItem.address
    }
    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: PageDataItem, newItem: PageDataItem): Boolean {
        return oldItem == newItem
    }
}


sealed class PageDataItem {
    data class SleepNightItem(val addressBean: PageStatusModel): PageDataItem() {
        override val address = addressBean
    }

    object Footer: PageDataItem(){
        override val address = PageStatusModel()
    }

    abstract val address: PageStatusModel
}

