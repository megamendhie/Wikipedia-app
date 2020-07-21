package com.sqube.wikipedia

import adapters.ListItemAdapter
import android.app.SearchManager
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.ListAdapter
import android.widget.SearchView
import kotlinx.android.synthetic.main.activity_search.*
import providers.ArticleDataProvider

class SearchActivity : AppCompatActivity() {
    private val provider: ArticleDataProvider = ArticleDataProvider()
    private val  adapter: ListItemAdapter = ListItemAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        listSearchResults.layoutManager = LinearLayoutManager(this)
        listSearchResults.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId==android.R.id.home)
            finish()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu!!.findItem(R.id.action_search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = searchItem.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setIconifiedByDefault(false)
        searchView.requestFocus()
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {

                //do the search and update the elements

                provider.search(query, 0, 20) { wikiResult ->
                    adapter.currentResults.clear()
                    adapter.currentResults.addAll(wikiResult.query!!.pages)
                    runOnUiThread { adapter.notifyDataSetChanged() }
                }
                println("updated search")
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}
