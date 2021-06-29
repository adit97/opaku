package com.opaku.id.core.ui.binding

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
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
    resource: String?
) {
    if (resource != null) {
        val drawable = Drawable.createFromResourceStream(
            resources,
            TypedValue(),
            resources.assets.open("img/$resource"),
            null
        )

        this.setImageDrawable(drawable)
    }
}

@BindingAdapter("setTextCurrency", "setFlagStrike")
fun AppCompatTextView.setTextCurrencyFormat(
    text: Double?,
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

@BindingAdapter("setBackgroundResource", "isSelected")
fun ShapeableImageView.setBackgroundResource(
    resource: String?,
    isSelected: Boolean?
) {
    if (resource != null) {
        if (isSelected != null && isSelected == true) {
            this.setBackgroundResource(R.color.white)
            this.strokeColor = ColorStateList.valueOf(Color.parseColor(resource))
        } else {
            this.setBackgroundColor(Color.parseColor(resource))
            this.setStrokeColorResource(R.color.white)
        }
    }
}