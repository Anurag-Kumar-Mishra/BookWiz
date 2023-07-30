package com.example.bookwiz.models

class Book (
    val ownerId: String = User().uid,
    val bookList : ArrayList<String> = ArrayList()
)