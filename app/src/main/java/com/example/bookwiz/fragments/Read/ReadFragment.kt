package com.example.bookwiz.fragments.Read

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookwiz.R
import com.example.bookwiz.adapters.BookListRVAdapter
import com.example.bookwiz.adapters.IBookListRVAdapter
import com.example.bookwiz.daos.BookDao
import com.example.bookwiz.daos.BookDetailsDao
import com.example.bookwiz.models.BookDetails
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.folioreader.FolioReader
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.sql.Time

class ReadFragment : Fragment(), IBookListRVAdapter {

    private lateinit var viewModel: ReadViewModel
    private lateinit var mAdapter : BookListRVAdapter
    private lateinit var bookDetailsDao: BookDetailsDao
    private lateinit var bookDao: BookDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.read_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ReadViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookDetailsDao = BookDetailsDao()
        bookDao = BookDao()
        val bookCollection = bookDetailsDao.bookCollection.orderBy("bookName",Query.Direction.ASCENDING)
        val recyclerViewOptions = FirestoreRecyclerOptions.Builder<BookDetails>()
            .setQuery(bookCollection, BookDetails::class.java).build()
        val recyclerView: RecyclerView = view.findViewById(R.id.bookListRecyclerView)
        mAdapter = BookListRVAdapter(recyclerViewOptions, this)
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.itemAnimator = null


    }

    override fun onBookNameClicked(bookId: String) {
        Toast.makeText(requireContext(),"Wait while we fetch eBook",Toast.LENGTH_SHORT).show()
        bookDetailsDao.getBookById(bookId).addOnSuccessListener {
            val bookId = it["bookNumber"]
            val bookName = it["bookName"] as String
            val storageRef = FirebaseStorage.getInstance().reference.child("$bookId.epub")

            val localFile = File.createTempFile("temp",".epub")
            storageRef.getFile(localFile).addOnSuccessListener {

                bookDao.addBook(bookName)
                val folioReader = FolioReader.get()
                folioReader.openBook(localFile.canonicalPath)

            }.addOnFailureListener{
                Toast.makeText(requireContext(),"Failed to fetch eBook",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {

        super.onStart()
        mAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        mAdapter.stopListening()
    }

}