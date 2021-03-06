package com.opaku.id.ui.dashboard.fragment.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.opaku.id.BR
import com.opaku.id.R
import com.opaku.id.core.domain.model.CartItemModel
import com.opaku.id.databinding.ItemCartBinding

class CartRecyclerViewAdapter(
    private val onClickListener: (CartItemModel, Int) -> Unit,
    private val onAdd: (CartItemModel) -> Unit,
    private val onSubtract: (CartItemModel) -> Unit,
    private val onDelete: (CartItemModel) -> Unit
) : RecyclerView.Adapter<CartRecyclerViewAdapter.Holder>() {

    private val items = mutableListOf<CartItemModel>()

    class Holder(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            model: CartItemModel,
            onAdd: (CartItemModel) -> Unit,
            onSubtract: (CartItemModel) -> Unit,
            onDelete: (CartItemModel) -> Unit
        ) = binding.apply {
            setVariable(BR.itemModel, model)
            executePendingBindings()

            if (this is ItemCartBinding) {
                ivDeleteProduct.setOnClickListener {
                    onDelete(model)
                }

                tvAdd.setOnClickListener {
                    onAdd(model)
                }

                tvSubtract.setOnClickListener {
                    onSubtract(model)
                }
            }
        }
    }

    fun populateItems(listItem: List<CartItemModel>) {
        items.clear()
        items.addAll(listItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(inflater, R.layout.item_cart, parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currentItem = items[position]

        holder.bind(currentItem, onAdd, onSubtract, onDelete).run {
            root.setOnClickListener { onClickListener(currentItem, position) }
        }
    }

    override fun getItemCount(): Int = items.size
}