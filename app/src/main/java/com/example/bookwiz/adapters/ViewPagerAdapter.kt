package com.example.bookwiz.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.bookwiz.R

class ViewPagerAdapter(private var instructionText: List<String>,private var imageUrls: List<String>, val listener: IPageAdapter) : RecyclerView.Adapter<ViewPagerAdapter.Pager2ViewHolder>(){

    inner class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder (itemView) {
        val pageText: TextView = itemView.findViewById(R.id.textOnBoarding)
        val getStartedButton: Button = itemView.findViewById(R.id.getStartedButton)
        val pageImage: ImageView = itemView.findViewById(R.id.imageOnBoarding)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Pager2ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_page,parent,false)
        return Pager2ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {
        val currentText = instructionText[position]
        val currentUrl = imageUrls[position]
        holder.pageText.text = currentText
        Glide.with(holder.itemView).load(currentUrl).override(Target.SIZE_ORIGINAL).into(holder.pageImage)
        if(position==itemCount-1){
            holder.getStartedButton.visibility = View.VISIBLE
            holder.getStartedButton.isClickable = true
        }
        else {
            holder.getStartedButton.visibility = View.GONE
            holder.getStartedButton.isClickable = false
        }
        holder.getStartedButton.setOnClickListener {
            listener.onGetStartedClicked()
        }
    }

    override fun getItemCount(): Int {
        return instructionText.size
    }
}
interface IPageAdapter{
    fun onGetStartedClicked()
}