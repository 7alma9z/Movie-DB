package com.salman.tmdb.utils

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.Checkable
import androidx.appcompat.widget.AppCompatImageButton
import com.salman.tmdb.R

/**
@author Salman Aziz
created on 7/15/22
 **/


  class ToggleImageButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatImageButton(context, attrs, defStyle), Checkable {
      var onCheckedChangeListener: OnCheckedChangeListener? =null


      fun setChecked(attrs: AttributeSet) {
        val a: TypedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ToggleImageButton)
     isChecked = a.getBoolean(R.styleable.ToggleImageButton_android_checked, false)
        a.recycle()
    }

    override fun isChecked(): Boolean {
        return isSelected
    }

    override fun setChecked(checked: Boolean) {
        isSelected = checked

        onCheckedChangeListener?.onCheckedChanged(this, checked)
    }

    override fun toggle() {
        isChecked = !isChecked()
    }

    override fun performClick(): Boolean {
        toggle()
        return super.performClick()
    }


    interface OnCheckedChangeListener {
        fun onCheckedChanged(buttonView: ToggleImageButton?, isChecked: Boolean)
    }
}