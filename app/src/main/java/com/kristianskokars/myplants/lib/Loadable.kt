package com.kristianskokars.myplants.lib

/** Indicates that a resource has a potential loading and error state before the data can be accessed. */
sealed class Loadable<out T> {
    data object Loading : Loadable<Nothing>()
    data class Error(val message: UIText? = null) : Loadable<Nothing>()
    data class Data<T>(val data: T) : Loadable<T>()
}
