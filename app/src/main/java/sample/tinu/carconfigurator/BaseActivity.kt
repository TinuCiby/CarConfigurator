package sample.tinu.carconfigurator

import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout


/**
 * Created by Tinu Ciby (tciby@chip.de) on 2020-02-20.
 */
abstract class BaseActivity : AppCompatActivity() {

    var mProgressBar: ProgressBar? = null
    override fun setContentView(layoutResID: Int) {

        val constraintLayout: ConstraintLayout = layoutInflater.inflate(R.layout.activity_base, null) as ConstraintLayout
        val frameLayout: FrameLayout = constraintLayout.findViewById(R.id.activity_content)
        mProgressBar = constraintLayout.findViewById(R.id.progress_bar)
        layoutInflater.inflate(layoutResID, frameLayout, true)
        super.setContentView(constraintLayout)
    }

    fun showProgressBar(visibility: Boolean) {
        mProgressBar?.visibility = if (visibility) View.VISIBLE else View.INVISIBLE
    }
}
