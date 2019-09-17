package adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sqube.wikipedia.R
import holders.CardViewHolder
import models.WikiPage

class ArticleCardAdapter : RecyclerView.Adapter<CardViewHolder>() {
    var currentResults: ArrayList<WikiPage> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val cardItem = LayoutInflater.from(parent.context).inflate(R.layout.article_card_item, parent, false)
        return CardViewHolder(cardItem)
    }

    override fun getItemCount(): Int {
        return currentResults.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, postion: Int) {
        val page: WikiPage = currentResults[postion]
        holder.updateWikiPage(page)
    }
}