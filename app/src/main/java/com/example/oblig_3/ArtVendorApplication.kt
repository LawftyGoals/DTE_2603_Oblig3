package com.example.oblig_3

import android.app.Application
import com.example.oblig_3.ui.data.AppContainer
import com.example.oblig_3.ui.data.AppDataContainer

class ArtVendorApplication: Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate(){
        super.onCreate()
        appContainer = AppDataContainer(this)
    }

}