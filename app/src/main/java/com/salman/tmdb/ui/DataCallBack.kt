package com.salman.tmdb.ui

import com.salman.remote.DataState

interface DataCallBack {
    fun setHeader(headerPath:String)
    fun onError(messages: DataState.CustomMessages)
    fun onMovieSelection(id:Int)
}