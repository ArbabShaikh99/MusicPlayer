package com.example.musicplayerapp.presentationlayer

data class HelloWorld (
    val message: String = "Hello, World!",
    val Status: String = "Success",
    val code: Int = 200,
    val data: List<String> = listOf("Item1", "Item2", "Item3")
)