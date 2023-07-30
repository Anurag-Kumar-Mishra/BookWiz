package com.example.bookwiz.fragments.Home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bookwiz.R
import com.example.bookwiz.adapters.BooksReadRVAdapter
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.ktx.Firebase
import kotlin.math.roundToInt

class HomeFragment : Fragment() {

    private lateinit var mAdapter: BooksReadRVAdapter

    private lateinit var viewModel: HomeViewModel
    private val db = FirebaseFirestore.getInstance()
    private val bookCollection = db.collection("books")
    private val userCollection = db.collection("users")
    private val auth = Firebase.auth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name: TextView = view.findViewById(R.id.textViewUserName)
        val userImage: ImageView = view.findViewById(R.id.imageViewUser)
        val currentUser = auth.currentUser!!.uid
        var userName: String
        var imageUrl: String
        var booksReadList: ArrayList<String>
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewBooksRead)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.itemAnimator = null

        val query: Task<QuerySnapshot> =
            bookCollection.whereEqualTo("ownerId", currentUser).get()
        query.addOnSuccessListener {
            booksReadList = it.documents[0]["bookList"] as ArrayList<String>
            mAdapter = BooksReadRVAdapter(requireContext(),booksReadList)
            recyclerView.adapter = mAdapter
        }

        val query1: Task<QuerySnapshot> =
            userCollection.whereEqualTo("uid", currentUser).get()
        query1.addOnSuccessListener {
            userName = it.documents[0]["displayName"].toString()
            imageUrl = it.documents[0]["imageUrl"].toString()

            name.text = userName
            Glide.with(this).load(imageUrl).circleCrop().into(userImage)
        }

    }

}