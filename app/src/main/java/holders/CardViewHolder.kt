package holders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.sqube.wikipedia.R
import kotlinx.android.synthetic.main.article_card_item.view.*

class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val articleImage: ImageView = itemView.findViewById(R.id.imgArticle)
    private val articleText: TextView = itemView.findViewById(R.id.txtArticle)
}