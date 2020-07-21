package com.sqube.wikipedia.fragments


import com.sqube.wikipedia.adapters.ArticleCardAdapter
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sqube.wikipedia.R
import com.sqube.wikipedia.WikiApplication
import com.sqube.wikipedia.activities.SearchActivity
import com.sqube.wikipedia.managers.WikiManager


class ExploreFragment : Fragment() {
    private var wikiManager: WikiManager? = null
    private var searchCardView: CardView? = null
    private var listExplore: RecyclerView? = null
    private var refresher: SwipeRefreshLayout? = null
    private var adapter: ArticleCardAdapter = ArticleCardAdapter()

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        wikiManager = (activity!!.applicationContext as WikiApplication).wikiManager
    }

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
        listExplore!!.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
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
            wikiManager?.getRandom(15) { wikiResult ->
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
