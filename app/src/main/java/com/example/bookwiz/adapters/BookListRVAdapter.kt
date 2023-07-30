package com.example.bookwiz.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookwiz.R
import com.example.bookwiz.models.BookDetails
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class BookListRVAdapter (options: FirestoreRecyclerOptions<BookDetails>, private val listener: IBookListRVAdapter):
    FirestoreRecyclerAdapter<BookDetails, BookListRVAdapter.BookListViewHolder>(options){

    inner class BookListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val bookName: TextView = itemView.findViewById(R.id.bookListName)
        val bookGenre: TextView = itemView.findViewById(R.id.bookListGenre)
        val bookCard: CardView = itemView.findViewById(R.id.bookCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookListViewHolder {
        val viewHolder = BookListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_book_details,parent,false))
        viewHolder.bookCard.setOnClickListener{
            listener.onBookNameClicked(snapshots.getSnapshot(viewHolder.adapterPosition).id)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: BookListViewHolder, position: Int, model: BookDetails) {
        holder.bookName.text = model.bookName
        holder.bookGenre.text = model.bookGenre
    }

}

interface IBookListRVAdapter {
    fun onBookNameClicked(bookId: String)
}