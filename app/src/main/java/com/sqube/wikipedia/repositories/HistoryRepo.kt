package com.sqube.wikipedia.repositories

import com.google.gson.Gson
import com.sqube.wikipedia.models.WikiPage
import com.sqube.wikipedia.models.WikiThumbnail
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select

class HistoryRepo (val databaseHelper: ArticleDatabaseOpenhelper){
    private val TABLE_NAME: String = "History"

    fun addHistory (wikiPage: WikiPage){
        databaseHelper.use {
            insert(TABLE_NAME,
                "id" to wikiPage.pageid,
                "title" to wikiPage.title,
                "url" to wikiPage.fullurl,
                "thumbnailJson" to Gson().toJson(wikiPage.thumbnail))
        }
    }

    fun removeHistoryById(pageId: Int){
        databaseHelper.use {
            delete(TABLE_NAME, "id = {pageid}", "pageid" to pageId)
        }
    }

    fun getAllHistory(): ArrayList<WikiPage>{
        var pages = ArrayList<WikiPage>()

        val articleRowParser = rowParser{ id: Int, title: String, url: String, thumbnailJson: String ->
            val page = WikiPage()
            page.title = title
            page.pageid = id
            page.fullurl = url
            page.thumbnail = Gson().fromJson(thumbnailJson, WikiThumbnail::class.java)

            pages.add(page)
        }

        databaseHelper.use {
            select(TABLE_NAME).parseList(articleRowParser)
        }

        return pages
    }
}