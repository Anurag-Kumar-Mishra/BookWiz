package com.example.bookwiz.daos

import com.example.bookwiz.models.Book
import com.example.bookwiz.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class BookDao {
    private val db = FirebaseFirestore.getInstance()
    private val bookCollection = db.collection("books")
    private val auth = Firebase.auth

    fun checkBook() {

        var contains = false
        val currentUser = auth.currentUser!!.uid
        val query: Task<QuerySnapshot> = bookCollection.whereEqualTo("ownerId", currentUser).get()
        query.addOnSuccessListener {
            if (!(it.isEmpty)) {
                contains = true
            }
            if (!contains) {
                addBookProfile()
            }
        }
    }

    fun getBookById(bookId: String): Task<DocumentSnapshot>{
        return bookCollection.document(bookId).get()
    }

    private fun addBookProfile() {
        val currentUser = auth.currentUser!!.uid
        GlobalScope.launch {
            val userDao = UserDao()
            val user = userDao.getUserById(currentUser).await()
                .toObject(User::class.java)!!
            val book = Book(user.uid)
            bookCollection.document().set(book)
        }
    }

    fun addBook(bookAdd: String) {
        GlobalScope.launch {

            val currentUser = auth.currentUser!!.uid
            var currentBook = ""
            var bookList: ArrayList<String>
            var book: Book
            val query: Task<QuerySnapshot> =
                bookCollection.whereEqualTo("ownerId", currentUser).get()
            query.addOnSuccessListener {
                currentBook = it.documents[0].id
                bookList = it.documents[0]["bookList"] as ArrayList<String>
                if(!(bookList.contains(bookAdd))){
                    bookList.add(bookAdd)
                    book = Book(currentUser,bookList)
                    bookCollection.document(currentBook).set(book)
                }

            }
        }

    }

}