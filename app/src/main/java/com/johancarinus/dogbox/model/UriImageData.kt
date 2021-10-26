package com.johancarinus.dogbox.model

import android.net.Uri
import androidx.annotation.DrawableRes

data class UriImageData(var uri:Uri, @DrawableRes var placeholderRes: Int) {

    /**
     * To avoid having to define a hashcode method for this class, this method is being defined
     * instead of overriding the equals function.
     **/
    fun isContentEqualTo(other: UriImageData): Boolean {
        var isEqualTo = true;
        isEqualTo = isEqualTo && this.uri == other.uri
        isEqualTo = isEqualTo && this.placeholderRes == other.placeholderRes
        return isEqualTo
    }
}
