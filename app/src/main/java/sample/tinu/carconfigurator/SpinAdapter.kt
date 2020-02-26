package sample.tinu.carconfigurator

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import sample.tinu.carconfigurator.models.FuelTypes
import sample.tinu.carconfigurator.models.Make
import sample.tinu.carconfigurator.models.Model

/**
 * Created by Tinu Ciby (tciby@chip.de) on 2020-02-23.
 */
class SpinAdapter<T>(context: Context, textViewResourceId: Int, private val values: List<T>) :
    ArrayAdapter<T>(context, textViewResourceId, values) {
    override fun getCount(): Int {
        return values.size
    }

    override fun getItem(position: Int): T {
        return values[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = TextView(context)
        label.gravity = Gravity.RIGHT
        label.setTextColor(Color.BLACK)
        if (values[position] is Make) {
            label.setText((values.toMutableList()[position] as Make).make)
        }
        if (values[position] is Model) {
            label.setText((values.toMutableList()[position] as Model).model)
        }
        if (values[position] is FuelTypes) {
            label.setText((values.toMutableList()[position] as FuelTypes).displayName)
        }
        return label
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = TextView(context)
        label.setTextColor(Color.BLACK)
        if (values[position] is Make) {
            label.setText((values.toMutableList()[position] as Make).make)
        }
        if (values[position] is Model) {
            label.setText((values.toMutableList()[position] as Model).model)
        }
        if (values[position] is FuelTypes) {
            label.setText((values.toMutableList()[position] as FuelTypes).displayName)
        }
        return label
    }

}