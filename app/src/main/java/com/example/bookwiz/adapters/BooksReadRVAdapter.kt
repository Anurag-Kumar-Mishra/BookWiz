package com.example.bookwiz.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookwiz.R

class BooksReadRVAdapter(private val context: Context, private val bookList: ArrayList<String>?): RecyclerView.Adapter<BooksReadViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksReadViewHolder {
        return BooksReadViewHolder(LayoutInflater.from(context).inflate(R.layout.item_books_read,parent,false))
    }

    override fun onBindViewHolder(holder: BooksReadViewHolder, position: Int) {
        val bookName = bookList?.get(position)
        val bookNumber:Int = position+1
        holder.bookReadName.text = bookName
        holder.bookReadNumber.text = "$bookNumber. "
    }

    override fun getItemCount(): Int {
        return bookList?.size ?: 0
    }
}

class BooksReadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val bookReadName: TextView = itemView.findViewById(R.id.bookReadName)
    val bookReadNumber: TextView = itemView.findViewById(R.id.bookReadNumber)
}
