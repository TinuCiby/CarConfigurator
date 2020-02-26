package sample.tinu.carconfigurator.models

/**
 * Created by Tinu Ciby (tciby@chip.de) on 2020-02-20.
 */
data class Model(
    var id: Int,
    var model: String?,
    var fuelTypes: MutableList<FuelTypes>
)

