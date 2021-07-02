package com.opaku.id.core.ui.binding

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.opaku.id.R
import com.opaku.id.core.ui.recyclerview.GeneralRecyclerViewAdapter
import com.opaku.id.core.utils.setImageDrawableFromServer
import com.opaku.id.core.utils.toDp
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
        setImageDrawableFromServer(resource)
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

@BindingAdapter("populateReviewImage")
fun LinearLayoutCompat.populateReviewImage(
    resource: List<String>?
) {
    val lp = LinearLayoutCompat.LayoutParams(context.toDp(48), context.toDp(48))
    lp.setMargins(0, 0, 16, 0)
    resource?.forEach {
        val iv = ShapeableImageView(context)
        iv.layoutParams = lp
        iv.scaleType = ImageView.ScaleType.CENTER_CROP
        iv.shapeAppearanceModel = iv.shapeAppearanceModel.toBuilder().setAllCornerSizes(16f).build()
        iv.setImageDrawableFromServer(it)
        this.addView(iv)
    }
}