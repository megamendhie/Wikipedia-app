package fragments


import adapters.ArticleCardAdapter
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sqube.wikipedia.R
import com.sqube.wikipedia.SearchActivity
import providers.ArticleDataProvider


class ExploreFragment : Fragment() {
    private val provider: ArticleDataProvider = ArticleDataProvider()
    private var searchCardView: CardView? = null
    private var listExplore: RecyclerView? = null
    private var refresher: SwipeRefreshLayout? = null
    private var adapter: ArticleCardAdapter = ArticleCardAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_explore, container, false)
        searchCardView = view.findViewById(R.id.search_cardView)
        listExplore = view.findViewById(R.id.listExplore)
        refresher = view.findViewById(R.id.refresher)

        //set onClick listener
        searchCardView!!.setOnClickListener {
            context!!.startActivity(Intent(context, SearchActivity::class.java))
        }

        //set layout manager and adapter for the recyclerView
        listExplore!!.layoutManager = LinearLayoutManager(context)
        listExplore!!.adapter = adapter

        refresher!!.setOnRefreshListener {
            refresher!!.isRefreshing = true
            getRandomArticles()
        }
        getRandomArticles()
        return view
    }

    private fun getRandomArticles(){
        try{
            provider.getRandom(15) { wikiResult ->
                adapter.currentResults.clear()
                adapter.currentResults.addAll(wikiResult.query!!.pages)
                activity!!.runOnUiThread {
                    adapter.notifyDataSetChanged()
                    refresher!!.isRefreshing = false
                }
            }
        }
        catch (ex: Exception){
            val builder = AlertDialog.Builder(context)
            builder.setMessage(ex.message).setTitle("oops!")
            val dialog = builder.create()
            dialog.show()
        }
    }
}
