package com.example.project_uas_036

import android.app.Application
import com.example.project_uas_036.dependenciesinjection.AppContainer
import com.example.project_uas_036.dependenciesinjection.BukuContainer

class PerpusApp : Application(){

    lateinit var bukcontainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        bukcontainer = BukuContainer()

    }

}