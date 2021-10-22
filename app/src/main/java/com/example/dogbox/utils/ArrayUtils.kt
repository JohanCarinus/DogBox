package com.example.dogbox.utils

fun IntArray.minWithIndex(): Int {
    return this.withIndex()
        .minWithOrNull(
            comparator = { item1: IndexedValue<Int>, item2: IndexedValue<Int> ->
                when {
                    item1.value < item2.value -> -1
                    item1.value == item2.value -> 0
                    else -> 1
                }
            }
        )?.index ?: 0
}