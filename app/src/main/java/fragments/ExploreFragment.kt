package fragments


import adapters.ArticleCardAdapter
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sqube.wikipedia.R
import com.sqube.wikipedia.SearchActivity
import kotlinx.android.synthetic.main.fragment_explore.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ExploreFragment : Fragment() {
    var searchCardView: CardView? = null
    var listExplore: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_explore, container, false)
        searchCardView = view.findViewById(R.id.search_cardView)
        listExplore = view.findViewById(R.id.listExplore)

        //set onClick listener
        searchCardView!!.setOnClickListener {
            val searchIntent = Intent(context, SearchActivity::class.java)
            context!!.startActivity(searchIntent)
        }

        //set layout manager and adapter for the recyclerView
        listExplore!!.layoutManager = LinearLayoutManager(context)
        listExplore!!.adapter = ArticleCardAdapter()

        return view
    }


}
