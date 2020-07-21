package com.sqube.wikipedia

import android.app.Application
import com.sqube.wikipedia.managers.WikiManager
import com.sqube.wikipedia.providers.ArticleDataProvider
import com.sqube.wikipedia.repositories.ArticleDatabaseOpenhelper
import com.sqube.wikipedia.repositories.FavouritesRepo
import com.sqube.wikipedia.repositories.HistoryRepo

class WikiApplication: Application() {
    private var dbHelper: ArticleDatabaseOpenhelper? = null
    private var favouritesRepo: FavouritesRepo? = null
    private var historyRepo: HistoryRepo? = null
    private var wikiProvider: ArticleDataProvider? = null
    var wikiManager: WikiManager? = null
            private set

    override fun onCreate() {
        super.onCreate()

        dbHelper = ArticleDatabaseOpenhelper(applicationContext)
        favouritesRepo = FavouritesRepo(dbHelper!!)
        historyRepo = HistoryRepo(dbHelper!!)
        wikiProvider = ArticleDataProvider()
        wikiManager = WikiManager(wikiProvider!!, favouritesRepo!!, historyRepo!!)
    }
}