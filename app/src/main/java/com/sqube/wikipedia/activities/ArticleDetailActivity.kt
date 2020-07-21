package com.sqube.wikipedia

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_article_detail.*
import models.WikiPage

class ArticleDetailActivity : AppCompatActivity() {
    private var currentPage: WikiPage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //get the page from the extra
        val wikiPageJson = intent.getStringExtra("page")
        currentPage = Gson().fromJson<WikiPage>(wikiPageJson, WikiPage::class.java)
        wvDetail?.webViewClient = object: WebViewClient(){
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return false
            }
        }
        wvDetail.loadUrl(currentPage!!.fullurl)
        Log.i("ArticleDetails", " Full url-> "+ currentPage!!.fullurl)
        Log.i("ArticleDetails", " wikipage-> "+ currentPage!!.toString())
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == android.R.id.home)
            finish()
        return true
    }
}