package providers

import android.util.Log
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.core.isSuccessful
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import models.Urls
import models.WikiResult
import java.io.Reader

class ArticleDataProvider {

    init{
        FuelManager.instance.baseHeaders = mapOf("User-Agent" to "SwiftQube Wikipedia")
    }

    fun search(term: String, skip: Int, take: Int, responseHandler : (result: WikiResult) -> Unit?){
        Urls.getSearchUrl(term, skip, take).httpGet()
            .responseObject(WikipediaDataDeserializer()){ _, _, result->

                when(result){
                    is Result.Failure ->{
                        Log.i("ErrorMsg", result.getException().message)
                        result.getException().stackTrace
                        throw Exception(result.getException())
                    }

                    is Result.Success ->{
                        val(data, _) = result
                        responseHandler.invoke(data as WikiResult)
                    }
                }
            }
    }

    fun getRandom(take: Int, responseHandler : (result: WikiResult) -> Unit){
        Urls.getRandomUrl(take).httpGet()
            .responseObject(WikipediaDataDeserializer()){ _, _, result->

                when(result){
                    is Result.Failure ->{
                        Log.i("ErrorMsg", result.getException().message)
                        result.getException().stackTrace
                        //throw Exception(result.getException())
                    }

                    is Result.Success ->{
                        val(data, _) = result
                        responseHandler.invoke(data as WikiResult)
                    }
                }
            }
    }

    class WikipediaDataDeserializer : ResponseDeserializable<WikiResult>{
        override fun deserialize(reader: Reader) = Gson().fromJson(reader, WikiResult::class.java)
    }
}