package com.sqube.wikipedia.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sqube.wikipedia.R
import com.sqube.wikipedia.WikiApplication
import com.sqube.wikipedia.adapters.ArticleCardAdapter
import com.sqube.wikipedia.managers.WikiManager
import com.sqube.wikipedia.models.WikiPage
import org.jetbrains.anko.doAsync

class FavouritesFragment : Fragment() {
    private var wikiManager: WikiManager? = null
    private var listFavourites : RecyclerView? = null
    private val adapter = ArticleCardAdapter()

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        wikiManager = (activity!!.applicationContext as WikiApplication).wikiManager
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_favourites, container, false)

        listFavourites = view.findViewById(R.id.listFavourites)
        listFavourites!!.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        listFavourites!!.adapter = adapter

        return view
    }

    override fun onResume() {
        super.onResume()

        doAsync {
            val favoriteArticles = wikiManager!!.getFavourites()
            adapter.currentResults.clear()
            adapter.currentResults.addAll(favoriteArticles as ArrayList<WikiPage>)
            activity.run { adapter.notifyDataSetChanged() }
        }
    }

}
