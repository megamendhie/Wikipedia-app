package fragments


import adapters.ArticleCardAdapter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sqube.wikipedia.R

class HistoryFragment : Fragment() {
    private var listHistory : RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_history, container, false)
        listHistory = view.findViewById(R.id.listHistory)

        listHistory!!.layoutManager = LinearLayoutManager(context)
        listHistory!!.adapter = ArticleCardAdapter()

        return view
    }


}
