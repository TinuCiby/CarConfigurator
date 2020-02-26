package sample.tinu.carconfigurator

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_car_configurator.*
import sample.tinu.carconfigurator.models.FuelTypes
import sample.tinu.carconfigurator.models.Make
import sample.tinu.carconfigurator.models.Model
import sample.tinu.carconfigurator.viewmodels.CarConfiguratorViewModel


class CarConfiguratorActivity : BaseActivity(), OnItemSelectedListener {

    var mCarConfiguratorViewModel = CarConfiguratorViewModel()
    var mSharedPreferences: SharedPreferences? = null
    var mEdit: SharedPreferences.Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_configurator)
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        mEdit = mSharedPreferences?.edit()

        spinnerMake.onItemSelectedListener = this
        spinnerModel.onItemSelectedListener = this
        spinnerFuelType.onItemSelectedListener = this
        btnConfigure.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                mEdit?.putInt("selected_make", spinnerMake.selectedItemPosition)
                mEdit?.putInt("selected_model", spinnerModel.selectedItemPosition)
                mEdit?.putInt("selected_fuel", spinnerFuelType.selectedItemPosition)
                mEdit?.commit()
            }
        })

        mCarConfiguratorViewModel =
            ViewModelProviders.of(this).get(CarConfiguratorViewModel().javaClass)
        showProgressBar(true)
        makeRetrofitRequest()
        subscribeObserver()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, position: Int, p3: Long) {

        when (parent?.getId()) {
            R.id.spinnerMake -> {
                val make: Make = spinnerMake.selectedItem as Make
                modelRetrofitRequest(make.id, "models")
            }
            R.id.spinnerModel -> {
                setFuelTypeSpinner()
            }
        }
    }

    fun subscribeObserver() {

        mCarConfiguratorViewModel.makes.observe(this, object : Observer<List<Make>> {

            override fun onChanged(makes: List<Make>?) {
                if (makes != null) {
                    mCarConfiguratorViewModel.setMdidRetrieveMake(true)
                    val dataAdapter = SpinAdapter<Make>(
                        applicationContext,
                        android.R.layout.simple_spinner_item,
                        makes
                    )

                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerMake.adapter = dataAdapter

                    val selectedMake = mSharedPreferences?.getInt("selected_make", 0)
                    if (makes.size > selectedMake!!) {
                        spinnerMake.setSelection(selectedMake)
                    }

                    showProgressBar(false)
                    showParent(true)
                }
            }
        })

        mCarConfiguratorViewModel.models.observe(this, object : Observer<List<Model>> {

            override fun onChanged(model: List<Model>?) {
                if (model != null) {

                    val modelAdapter = SpinAdapter<Model>(
                        applicationContext,
                        android.R.layout.simple_spinner_item,
                        model
                    )
                    modelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerModel.adapter = modelAdapter

                    val selectedModel = mSharedPreferences?.getInt("selected_model", 0)
                    if (model.size > selectedModel!!) {
                        spinnerModel.setSelection(selectedModel)
                    }
                }
            }
        })

        mCarConfiguratorViewModel.isMakeRequestTimeOut.observe(this, object : Observer<Boolean> {

            override fun onChanged(aBoolean: Boolean) {

                if (aBoolean && !mCarConfiguratorViewModel.ismDidRetrieveMake()) {
                    displayErrorScreen("Error retrieving data. Check network connection.")

                    showParent(false)
                    showProgressBar(false)
                }

            }
        })
    }

    private fun setFuelTypeSpinner() {


        val model = mCarConfiguratorViewModel.models.value
        var fuelTypes: List<FuelTypes>? = null

        for (index in 0 until model!!.size) {
            if (spinnerModel.selectedItem == model.get(index)) {
                fuelTypes = model.get(index).fuelTypes
            }
        }

        val fuelTypeAdapter = SpinAdapter<FuelTypes>(
            applicationContext,
            android.R.layout.simple_spinner_item, fuelTypes!!
        )
        fuelTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFuelType.adapter = fuelTypeAdapter


        val selectedFuel = mSharedPreferences?.getInt("selected_fuel", 0)
        if (fuelTypes.size > selectedFuel!!) {
            spinnerFuelType.setSelection(selectedFuel)
        }
    }

    private fun makesApi() {
        mCarConfiguratorViewModel.makesApi()
    }

    private fun modelApi(id: Int, model: String) {
        mCarConfiguratorViewModel.modelApi(id, model);
    }


    fun makeRetrofitRequest() {
        makesApi()
    }

    fun modelRetrofitRequest(id: Int, model: String) {
        modelApi(id, model)
    }


    fun showParent(visibility: Boolean) {
        container_layout?.visibility = if (visibility) View.VISIBLE else View.INVISIBLE
    }

    fun isDataSaved(): Boolean {

        val data = mSharedPreferences?.getInt("selected_model", -1)

        if (data == -1) {
            return false
        }
        return true
    }

    private fun displayErrorScreen(errorMessage: String) {

        val textView = TextView(this)
        if (errorMessage != "") {
            textView.text = errorMessage
        } else {
            textView.text = "Error"
        }
        textView.textSize = 15f
        textView.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        configurator_container.addView(textView)
    }
}
