package com.johancarinus.dogbox.model.paging

data class PageInfo(
    val count: Int,
    val pages: Int,
    val next: String,
    val previous: String
) { }