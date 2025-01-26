package com.example.project_uas_036

import android.app.Application
import com.example.project_uas_036.dependenciesinjection.AppContainer
import com.example.project_uas_036.dependenciesinjection.AppKategoriContainer
import com.example.project_uas_036.dependenciesinjection.BukuContainer
import com.example.project_uas_036.dependenciesinjection.KategoriContainer

class PerpusApp : Application(){

    lateinit var bukcontainer: AppContainer
    lateinit var katcontainer: AppKategoriContainer

    override fun onCreate() {
        super.onCreate()
        bukcontainer = BukuContainer()
        katcontainer = KategoriContainer()



    }

}