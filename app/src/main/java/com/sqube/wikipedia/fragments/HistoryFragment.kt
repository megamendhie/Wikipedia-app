package com.sqube.wikipedia.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*

import com.sqube.wikipedia.R
import com.sqube.wikipedia.WikiApplication
import com.sqube.wikipedia.adapters.ListItemAdapter
import com.sqube.wikipedia.managers.WikiManager
import com.sqube.wikipedia.models.WikiPage
import org.jetbrains.anko.*

class HistoryFragment : Fragment() {
    private var wikiManager: WikiManager? = null
    private var listHistory : RecyclerView? = null
    private val adapter = ListItemAdapter()

    init {
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        wikiManager = (activity!!.applicationContext as WikiApplication).wikiManager
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_history, container, false)
        listHistory = view.findViewById(R.id.listHistory)

        listHistory!!.layoutManager = LinearLayoutManager(context)
        listHistory!!.adapter = adapter

        return view
    }

    override fun onResume() {
        super.onResume()

        doAsync {
            val history = wikiManager!!.getHistory()
            adapter.currentResults.clear()
            adapter.currentResults.addAll(history as ArrayList<WikiPage>)
            activity.run { adapter.notifyDataSetChanged() }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == R.id.action_clear_history){
            activity!!.alert ("Are you sure you want tot clear history?" ){
                yesButton {
                    adapter.currentResults.clear()
                    doAsync {
                        wikiManager?.clearHistory()
                    }
                    activity!!.runOnUiThread {adapter.notifyDataSetChanged()}
                }
                noButton {  }
            }.show()
            wikiManager!!.clearHistory()
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.history_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
