package com.example.recyclerviewexample

data class ItemViewModel(
    val image : Int,
    val text : String,
    val detail : String,
    var isExpandale : Boolean = false
)