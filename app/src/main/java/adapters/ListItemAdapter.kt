package adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sqube.wikipedia.R
import holders.ListItemHolder
import models.WikiPage

class ListItemAdapter : RecyclerView.Adapter<ListItemHolder>() {
    var currentResults: ArrayList<WikiPage> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemHolder{
        val cardItem = LayoutInflater.from(parent.context).inflate(R.layout.article_list_item, parent, false)
        return ListItemHolder(cardItem)
    }

    override fun getItemCount(): Int {
        return currentResults.size
    }

    override fun onBindViewHolder(holder: ListItemHolder, postion: Int) {
        val page: WikiPage = currentResults[postion]
        holder.updateWikiPage(page)
    }
}