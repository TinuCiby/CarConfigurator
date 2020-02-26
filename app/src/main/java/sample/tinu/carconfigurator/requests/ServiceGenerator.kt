package sample.tinu.carconfigurator.requests

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sample.tinu.carconfigurator.util.Constants

/**
 * Created by Tinu Ciby (tciby@chip.de) on 2020-02-20.
 */
object ServiceGenerator {
    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
    private val retrofit = retrofitBuilder.build()
    val configuratorApi = retrofit.create(
        CarConfiguratorApi::class.java
    )

}