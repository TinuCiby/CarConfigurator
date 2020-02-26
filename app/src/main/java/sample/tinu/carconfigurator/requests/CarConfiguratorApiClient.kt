package sample.tinu.carconfigurator.requests

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Response
import sample.tinu.carconfigurator.AppExecutors
import sample.tinu.carconfigurator.models.Make
import sample.tinu.carconfigurator.models.Model
import sample.tinu.carconfigurator.requests.responses.MakesResponse
import sample.tinu.carconfigurator.requests.responses.ModelsResponse
import sample.tinu.carconfigurator.util.Constants
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Tinu Ciby (tciby@chip.de) on 2020-02-21.
 */
class CarConfiguratorApiClient {
    private val mMakes: MutableLiveData<List<Make>> = MutableLiveData()
    private val mModels: MutableLiveData<List<Model>> = MutableLiveData()
    private var mRetriveMakesRunnable: RetrieveMakesRunnable? = null
    private var mRetriveModelsRunnable: RetrieveModelsRunnable? = null
    private val mMakesRequestTimeout = MutableLiveData<Boolean>()

    val makes: LiveData<List<Make>> = mMakes
    val models: LiveData<List<Model>> = mModels
    val isMakeRequestTimeOut: LiveData<Boolean> = mMakesRequestTimeout

    //let the user konw its timed out.
    val makesApi: Unit
        get() {
            if (mRetriveMakesRunnable != null) {
                mRetriveMakesRunnable = null
            }
            mRetriveMakesRunnable = RetrieveMakesRunnable()
            val handler =
                AppExecutors.instance.networkIO().submit(mRetriveMakesRunnable)
            AppExecutors.instance.networkIO().schedule(
                {
                    mMakesRequestTimeout.postValue(true)
                    handler.cancel(true)
                },
                Constants.NETWORK_TIMEOUT.toLong(),
                TimeUnit.MILLISECONDS
            )
        }

    fun getModelsApi(id: Int?, model: String?) {
        if (mRetriveModelsRunnable != null) {
            mRetriveModelsRunnable = null
        }
        mRetriveModelsRunnable = RetrieveModelsRunnable(id, model)
        val handler =
            AppExecutors.instance.networkIO().submit(mRetriveModelsRunnable)
        AppExecutors.instance.networkIO().schedule(
            {
                handler.cancel(true)
            },
            Constants.NETWORK_TIMEOUT.toLong(),
            TimeUnit.MILLISECONDS
        )
    }

    private inner class RetrieveMakesRunnable : Runnable {

        override fun run() {
            try {
                val response: Response<*> = makes.execute()
                if (response.code() == 200) {
                    val list: List<Make> =
                        ArrayList((response.body() as MakesResponse?)!!.makes!!)
                    mMakes.postValue(list)
                } else {
                    val error = response.errorBody()!!.string()
                    Log.e(TAG, "run: $error")
                    mMakes.postValue(null)
                }
            } catch (e: IOException) {
                e.printStackTrace()
                mMakes.postValue(null)
            }
        }

        private val makes: Call<MakesResponse> = ServiceGenerator.configuratorApi.makes

    }

    private inner class RetrieveModelsRunnable(private val id: Int?, private val model: String?) :
        Runnable {

        override fun run() {
            try {
                val response: Response<*> = getModels(id, model).execute()
                if (response.code() == 200) {
                    val list: List<Model> =
                        ArrayList((response.body() as ModelsResponse?)!!.models!!)
                    mModels.postValue(list)
                } else {
                    val error = response.errorBody()!!.string()
                    Log.e(TAG, "run: $error")
                    mModels.postValue(null)
                }
            } catch (e: IOException) {
                e.printStackTrace()
                mModels.postValue(null)
            }
        }

        private fun getModels(id: Int?, model: String?): Call<ModelsResponse> {
            return ServiceGenerator.configuratorApi
                .getModels(id, model)
        }

    }

    companion object {
        var instance: CarConfiguratorApiClient = CarConfiguratorApiClient()
        private const val TAG = "ApiClient"
    }
}