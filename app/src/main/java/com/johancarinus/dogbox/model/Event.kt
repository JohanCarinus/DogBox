package com.johancarinus.dogbox.model

/**
 * Based on article: https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
 **/
class Event<out T>(private val data: T) {

    var hasBeenConsumed = false
        private set

    fun getDataIfNotConsumed(): T? {
        return if (hasBeenConsumed) {
            null
        } else {
            hasBeenConsumed = true
            data
        }
    }

    fun peekData(): T = data
}