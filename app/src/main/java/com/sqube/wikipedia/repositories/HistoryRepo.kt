package repositories

import com.google.gson.Gson
import models.WikiPage
import models.WikiThumbnail
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.rowParser

class HistoryRepo (val databaseHelper: ArticleDatabaseOpenhelper){
    private val TABLE_NAME: String = "History"

    fun addHistory (wikiPage: WikiPage){
        databaseHelper.use {
            insert(TABLE_NAME,
                "id" to wikiPage.pageId,
                "title" to wikiPage.title,
                "url" to wikiPage.fullurl,
                "thumbnailJson" to Gson().toJson(wikiPage.thumbnail))
        }
    }

    fun removeHistoryById(pageId: Int){
        databaseHelper.use {
            delete(TABLE_NAME, "id = {pageId}", "pageId" to pageId)
        }
    }

    fun isArticleFavourite(pageid: Int): Boolean{
        var pages = getAllHistory()
        return pages.any { page ->
            page.pageId == pageid
        }
    }

    fun getAllHistory(): ArrayList<WikiPage>{
        var pages = ArrayList<WikiPage>()

        val articleRowParser = rowParser{ id: Int, title: String, url: String, thumbnailJson: String ->
            val page = WikiPage()
            page.title = title
            page.pageId = id
            page.fullurl = url
            page.thumbnail = Gson().fromJson(thumbnailJson, WikiThumbnail::class.java)

            pages.add(page)
        }
        return pages
    }
}