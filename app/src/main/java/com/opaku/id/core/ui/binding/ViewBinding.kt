package com.opaku.id.core.ui.binding

import android.graphics.Paint
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.opaku.id.R
import com.opaku.id.core.ui.recyclerview.GeneralRecyclerViewAdapter
import java.text.DecimalFormat

@BindingAdapter(value = ["populateItems"])
fun RecyclerView.populateItems(
    items: List<Any>?
) {
    if (items != null) {
        (this.adapter as GeneralRecyclerViewAdapter<Any>).populateItems(items)
    }
}

@BindingAdapter(value = ["setImageResource"])
fun AppCompatImageView.setImageResource(
    resource: Int?
) {
    if (resource != null) {
        this.setImageResource(resource)
    }
}

@BindingAdapter("setTextCurrency", "setFlagStrike")
fun AppCompatTextView.setTextCurrencyFormat(
    text: Int?,
    isStrike: Boolean?
) {
    if (text != null) {
        val formatter = DecimalFormat("#,###")
        val formattedNumber = formatter.format(text)
        this.text = context.getString(R.string.item_product_original_price, formattedNumber)

        if (isStrike != null && isStrike == true) {
            this.paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }
    }
}