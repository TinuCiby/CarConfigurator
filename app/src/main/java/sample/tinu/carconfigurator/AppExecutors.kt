package sample.tinu.carconfigurator

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

/**
 * Created by Tinu Ciby (tciby@chip.de) on 2020-02-21.
 */
class AppExecutors {
    private val mNetworkIO = Executors.newScheduledThreadPool(3)

    fun networkIO(): ScheduledExecutorService {
        return mNetworkIO
    }

    companion object {
        var instance: AppExecutors = AppExecutors()
    }
}