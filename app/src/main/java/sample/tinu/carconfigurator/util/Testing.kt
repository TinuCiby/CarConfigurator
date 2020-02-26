package sample.tinu.carconfigurator.util

import android.util.Log
import sample.tinu.carconfigurator.models.Make
import sample.tinu.carconfigurator.models.Model

/**
 * Created by Tinu Ciby (tciby@chip.de) on 2020-02-22.
 */
object Testing {
    private const val TAG = "Testing"
    fun printMakes(list: List<Make>, tag: String?) {
        for (make in list) {
            Log.d(TAG, "onChanged: " + make.make)
        }
    }

    fun printModels(list: List<Model>, tag: String?) {
        for (model in list) {
            Log.d(TAG, "onChanged: " + model.model)
        }
    }
}