package com.example.bookwiz.daos

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class BookDetailsDao {
    val db = FirebaseFirestore.getInstance()
    val bookCollection = db.collection("bookList")

    fun getBookById(bookId: String): Task<DocumentSnapshot> {
        return bookCollection.document(bookId).get()
    }
}