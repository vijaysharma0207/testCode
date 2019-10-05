package com.codebrew.kc.app_util


import android.graphics.Paint
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.codebrew.kc.R


class BindingUtils {

    companion object {
        @JvmStatic
        @BindingAdapter("position")
        fun ConstraintLayout.setBackgroundAdapter(position: Int) {
            if (position.rem(2) == 0) {
                setBackgroundColor(ContextCompat.getColor(context, R.color.list_item_color))
            } else {
                setBackgroundColor(ContextCompat.getColor(context, R.color.white))
            }
        }


        @JvmStatic
        @BindingAdapter("status")
        fun TextView.setBackgroundAdapter(status: Boolean) {

            paintFlags = if (status) {
                setTextColor(ContextCompat.getColor(context, R.color.app_color))
                paintFlags or Paint.UNDERLINE_TEXT_FLAG
            } else {
                setTextColor(ContextCompat.getColor(context, R.color.text_color))
                paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
            }
        }
    }


}// This class is not publicly instantiable
