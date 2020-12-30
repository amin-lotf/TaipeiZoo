package com.example.taipeizooinfo.repository.util

sealed class DataState<T> {
    data class Success<T>(val data: T) : DataState<T>()
    data class Failed<T>(val message: String) : DataState<T>()

    companion object {
        fun <T> success(data: T) = Success(data)
        fun  <T> failed(message: String) = Failed<T>(message)
    }
}



open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}