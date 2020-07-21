package com.sqube.wikipedia.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.gson.Gson
import com.sqube.wikipedia.R
import com.sqube.wikipedia.WikiApplication
import com.sqube.wikipedia.managers.WikiManager
import kotlinx.android.synthetic.main.activity_article_detail.*
import com.sqube.wikipedia.models.WikiPage
import org.jetbrains.anko.toast

class ArticleDetailActivity : AppCompatActivity() {
    private var wikiManager: WikiManager? = null
    private var currentPage: WikiPage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)
        wikiManager = (applicationContext as WikiApplication).wikiManager

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //get the page from the extra
        val wikiPageJson = intent.getStringExtra("page")
        currentPage = Gson().fromJson<WikiPage>(wikiPageJson, WikiPage::class.java)
        supportActionBar?.title = currentPage?.title

        wvDetail?.webViewClient = object: WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return false
            }
        }
        wvDetail.loadUrl(currentPage!!.fullurl)
        wikiManager?.addHistory(currentPage!!)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == android.R.id.home)
            finish()
        else if(item!!.itemId==R.id.action_fav) {
            try {
            if(wikiManager!!.getIsFavourite(currentPage!!.pageid!!)){
                wikiManager!!.removeFavourite(currentPage!!.pageid!!)
                toast("Article removed from favourite")
                //Snackbar.make(wvDetail, "Article removed from favourite", Snackbar.LENGTH_SHORT)
            }
            else{
                wikiManager!!.addFavourite(currentPage!!)
                toast("Article added to favourite")
                //Snackbar.make(wvDetail, "Article added to favourite", Snackbar.LENGTH_SHORT)
            }

            }
            catch (ex: Exception){
                Log.i("ArticleActivity", ex.message.toString())
                ex.stackTrace
                toast("Unexpected error occurred")
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.article_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}