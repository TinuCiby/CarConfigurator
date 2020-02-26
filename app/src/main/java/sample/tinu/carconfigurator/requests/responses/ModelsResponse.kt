package sample.tinu.carconfigurator.requests.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import sample.tinu.carconfigurator.models.Model

/**
 * Created by Tinu Ciby (tciby@chip.de) on 2020-02-20.
 */
class ModelsResponse {
    @SerializedName("data")
    @Expose
    val models: List<Model>? = null

    override fun toString(): String {
        return "ModelsResponse{" +
                "data=" + models +
                '}'
    }
}