package managers

import models.WikiPage
import models.WikiResult
import providers.ArticleDataProvider
import repositories.FavouritesRepo
import repositories.HistoryRepo

class WikiManager (
    private val provider: ArticleDataProvider,
    private val favouriteRepo: FavouritesRepo,
    private val historyRepo: HistoryRepo) {

    private var favouriteCache: ArrayList<WikiPage>? = null
    private var historyCache: ArrayList<WikiPage>? = null

    fun search(term: String, skip: Int, take: Int, handler: (result: WikiResult) -> Unit?){
        return provider.search(term, skip, take, handler)
    }

    fun getRandom(take: Int, handler: (result: WikiResult) -> Unit?){
        return provider.getRandom(take, handler)
    }
    
    fun getHistory(): ArrayList<WikiPage>?{
        if(historyCache ==null)
            historyCache = historyRepo.getAllHistory()
        return historyCache
    }
    
    fun getFavourites(): ArrayList<WikiPage>?{
        if(favouriteCache == null)
            favouriteCache = favouriteRepo.getAllFavourites()
        return favouriteCache
    }
    
    fun addFavourite(page: WikiPage){
        favouriteCache?.add(page)
        favouriteRepo.addFavourites(page)
    }
    
    fun removeFavourite(pageId: Int){
        favouriteRepo.removeFavouriteById(pageId)
        favouriteCache = favouriteCache!!.filter { it.pageId != pageId } as ArrayList<WikiPage>
    }
    
    fun getIsFavourite(pageId: Int): Boolean{
        return favouriteRepo.isArticleFavourite(pageId)
    }

    fun addHistory(page: WikiPage){
        historyCache?.add(page)
        historyRepo.addHistory(page)
    }

    fun removeHistory(pageId: Int){
        historyRepo.removeHistoryById(pageId)
        historyCache = historyCache!!.filter { it.pageId != pageId } as ArrayList<WikiPage>
    }
    
    fun clearHistory(){
        historyCache?.clear()
        val allHistory = historyRepo.getAllHistory()
        allHistory?.forEach { historyRepo.removeHistoryById(it.pageId!!) }
    }

}