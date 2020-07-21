package fragments


import adapters.ListItemAdapter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sqube.wikipedia.R

class FavouritesFragment : Fragment() {
    private var listFavourites : RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_favourites, container, false)
        listFavourites = view.findViewById(R.id.listFavourites)

        listFavourites!!.layoutManager = LinearLayoutManager(context)
        listFavourites!!.adapter = ListItemAdapter()

        return view
    }


}
