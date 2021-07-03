package com.opaku.id.core.utils

import android.view.View

class ButtonClickListener(val onClick: (View) -> Unit) {
    fun onClickListener(view: View) {
        onClick(view)
    }
}