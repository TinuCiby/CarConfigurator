package sample.tinu.carconfigurator.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import sample.tinu.carconfigurator.models.Make
import sample.tinu.carconfigurator.models.Model
import sample.tinu.carconfigurator.repositories.CarConfiguratorRepository
import sample.tinu.carconfigurator.repositories.CarConfiguratorRepository.Companion.instance

/**
 * Created by Tinu Ciby (tciby@chip.de) on 2020-02-21.
 */
class CarConfiguratorViewModel : ViewModel() {
    private val mCarConfiguratorRepository: CarConfiguratorRepository = instance!!
    private var mDidRetrieveMake: Boolean = false
    val makes: LiveData<List<Make>> = mCarConfiguratorRepository.makes

    fun makesApi() {
        mCarConfiguratorRepository.makesApi
    }

    val models: LiveData<List<Model>> = mCarConfiguratorRepository.models

    val isMakeRequestTimeOut: LiveData<Boolean> = mCarConfiguratorRepository.isMakeRequestTimeOut

    fun modelApi(id: Int?, model: String?) {
        mCarConfiguratorRepository.getModelsApi(id, model)
    }

    fun ismDidRetrieveMake(): Boolean {
        return mDidRetrieveMake
    }

    fun setMdidRetrieveMake(mDidRetrieveMake: Boolean) {
        this.mDidRetrieveMake = mDidRetrieveMake
    }

}