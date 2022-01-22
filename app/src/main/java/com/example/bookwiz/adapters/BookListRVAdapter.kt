package com.example.bookwiz.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bookwiz.R
import com.example.bookwiz.models.BookDetails
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class BookListRVAdapter (options: FirestoreRecyclerOptions<BookDetails>, private val listener: IBookListRVAdapter):
    FirestoreRecyclerAdapter<BookDetails, BookListRVAdapter.BookListViewHolder>(options){

    inner class BookListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val bookName: TextView = itemView.findViewById(R.id.bookListName)
        val bookNumber: TextView = itemView.findViewById(R.id.bookListNumber)
        val bookGenre: TextView = itemView.findViewById(R.id.bookListGenre)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookListViewHolder {
        val viewHolder = BookListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_book_details,parent,false))
        viewHolder.bookName.setOnClickListener{
            listener.onBookNameClicked(snapshots.getSnapshot(viewHolder.adapterPosition).id)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: BookListViewHolder, position: Int, model: BookDetails) {
        holder.bookName.text = model.bookName
        holder.bookNumber.text = model.bookNumber.toString()
        holder.bookGenre.text = model.bookGenre.toString()
    }

}

interface IBookListRVAdapter {
    fun onBookNameClicked(bookId: String)
}