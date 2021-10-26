package com.johancarinus.dogbox.model.paging

import android.net.Uri

data class PagedResponse(val pageInfo: PageInfo, val results: List<Uri>) {

}