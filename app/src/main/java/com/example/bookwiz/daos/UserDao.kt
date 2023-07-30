package com.example.bookwiz.daos

import com.example.bookwiz.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserDao {
    private val db = FirebaseFirestore.getInstance()
    private val userCollection = db.collection("users")

    fun addUser(user: User?){
        userCollection.document(Firebase.auth.currentUser!!.uid).get().addOnCompleteListener {
            if (!it.result.exists())
                user?.let {
                    GlobalScope.launch(Dispatchers.IO) {
                        userCollection.document(user.uid).set(user, SetOptions.merge())
                    }
                }
        }
    }

    fun getUserById(uId: String) : Task<DocumentSnapshot> {
        return userCollection.document(uId).get()
    }
}