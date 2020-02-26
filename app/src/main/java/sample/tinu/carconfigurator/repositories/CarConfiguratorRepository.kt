package sample.tinu.carconfigurator.repositories

import androidx.lifecycle.LiveData
import sample.tinu.carconfigurator.models.Make
import sample.tinu.carconfigurator.models.Model
import sample.tinu.carconfigurator.requests.CarConfiguratorApiClient

/**
 * Created by Tinu Ciby (tciby@chip.de) on 2020-02-21.
 */
class CarConfiguratorRepository {

    private val mCarConfiguratorApiClient: CarConfiguratorApiClient = CarConfiguratorApiClient.instance

    val makes: LiveData<List<Make>> = mCarConfiguratorApiClient.makes

    val makesApi: Unit = mCarConfiguratorApiClient.makesApi

    val models: LiveData<List<Model>> = mCarConfiguratorApiClient.models

    val isMakeRequestTimeOut: LiveData<Boolean> = mCarConfiguratorApiClient.isMakeRequestTimeOut

    fun getModelsApi(id: Int?, model: String?) {
        mCarConfiguratorApiClient.getModelsApi(id, model)
    }

    companion object {
        var instance: CarConfiguratorRepository? = CarConfiguratorRepository()
    }
}