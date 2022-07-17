package com.salman.tmdb.extensions

import android.graphics.drawable.Drawable
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.salman.tmdb.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
@author Salman Aziz
created on 7/13/22
 **/


fun AppCompatImageView.setImageUrl(
    url: String? = null,
    default: Drawable? = null,
    placeholder: Drawable? = null
) {
    val glideRequest = if (default != null) Glide.with(context).load(default)
    else Glide.with(context).load(url)
    if (placeholder != null) glideRequest.thumbnail(
        Glide.with(context).load(placeholder).centerCrop()
    )

    glideRequest.into(this)
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.toggleVisibility() {
    this.visibility = View.GONE.takeIf { this.visibility == View.VISIBLE } ?: View.VISIBLE
}

fun View.showSnackBar(  message: String) {

    Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
        .setActionTextColor(ContextCompat.getColor(this.context, R.color.white)).also {
            it.setAction(
                "OK"
            ) { v ->
                it.dismiss()
            }
        }
        .show()


}
