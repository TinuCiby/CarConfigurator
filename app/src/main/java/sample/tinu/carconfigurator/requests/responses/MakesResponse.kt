package sample.tinu.carconfigurator.requests.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import sample.tinu.carconfigurator.models.Make

/**
 * Created by Tinu Ciby (tciby@chip.de) on 2020-02-20.
 */
class MakesResponse {
    @SerializedName("data")
    @Expose
    val makes: List<Make>? = null

    override fun toString(): String {
        return "MakesResponse{" +
                "makes=" + makes +
                '}'
    }
}