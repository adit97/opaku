package com.opaku.id.core.ui.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.opaku.id.BR

class GeneralRecyclerViewAdapter<T>(
    private val layoutId: Int,
    private val onClickListener: (T, Int) -> Unit,
    private val onLongClickListener: (Int) -> Unit
) :
    RecyclerView.Adapter<GeneralRecyclerViewAdapter.Holder<T>>() {

    private val items = mutableListOf<T>()

    class Holder<T>(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: T) = binding.apply {
            setVariable(BR.itemModel, model)
            executePendingBindings()
        }
    }

    fun populateItems(listItem: List<T>) {
        items.clear()
        items.addAll(listItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder<T> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, layoutId, parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder<T>, position: Int) {
        val currentItem = items[position]

        holder.bind(currentItem).run {
            root.setOnClickListener { onClickListener(currentItem, position) }
            root.setOnLongClickListener {
                onLongClickListener(position)
                return@setOnLongClickListener true
            }
        }
    }

    override fun getItemCount(): Int = items.size
}