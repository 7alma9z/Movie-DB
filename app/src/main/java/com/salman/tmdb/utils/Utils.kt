package com.salman.tmdb.utils

object Utils {
    const val W300="w300"
    const val W500="w500"
    fun getImagePath(size:String=W300,posterPath: String?): String {
        return "https://image.tmdb.org/t/p/$size$posterPath"
    }
}