package com.sqube.wikipedia.holders

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.sqube.wikipedia.activities.ArticleDetailActivity
import com.sqube.wikipedia.R
import com.sqube.wikipedia.models.WikiPage

class ListItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val resultIcon: ImageView = itemView.findViewById(R.id.result_icon)
    private val resultTitle: TextView = itemView.findViewById(R.id.result_title)
    private var currentPage: WikiPage? = null

    init{
        itemView.setOnClickListener{view: View? ->
            var detailPageIntent = Intent(itemView.context, ArticleDetailActivity::class.java)
            var pageJson = Gson().toJson(currentPage)
            detailPageIntent.putExtra("page", pageJson)
            itemView.context.startActivity(detailPageIntent)
        }
    }
    //update page info to the card item
    fun updateWikiPage (page: WikiPage){
        currentPage = page
        resultTitle.text = page.title
        if(page.thumbnail != null)
            Picasso.get().load(page.thumbnail!!.source).into(resultIcon)
    }
}