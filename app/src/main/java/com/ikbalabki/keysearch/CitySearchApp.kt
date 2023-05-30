package com.ikbalabki.keysearch

import android.app.Application
import com.ikbalabki.keysearch.data.CitiesRepo
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject




@HiltAndroidApp
class CitySearchApp : Application() {
    @Inject
    lateinit var repo: CitiesRepo

    override fun onCreate() {
        super.onCreate()
        repo.loadData()
    }
}