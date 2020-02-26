package sample.tinu.carconfigurator.requests

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import sample.tinu.carconfigurator.requests.responses.MakesResponse
import sample.tinu.carconfigurator.requests.responses.ModelsResponse

/**
 * Created by Tinu Ciby (tciby@chip.de) on 2020-02-20.
 */
interface CarConfiguratorApi {
    @get:GET("/vehicles/makes/")
    val makes: Call<MakesResponse>

    @GET("/vehicles/makes/{id}/{models}")
    fun getModels(@Path("id") id: Int?, @Path("models") models: String?): Call<ModelsResponse>
}