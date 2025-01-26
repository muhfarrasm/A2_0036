package com.example.project_uas_036

import android.app.Application
import com.example.project_uas_036.dependenciesinjection.AppContainer
import com.example.project_uas_036.dependenciesinjection.AppKategoriContainer
import com.example.project_uas_036.dependenciesinjection.AppPenerbitContainer
import com.example.project_uas_036.dependenciesinjection.AppPenulisContainer
import com.example.project_uas_036.dependenciesinjection.BukuContainer
import com.example.project_uas_036.dependenciesinjection.KategoriContainer
import com.example.project_uas_036.dependenciesinjection.PenerbitContainer
import com.example.project_uas_036.dependenciesinjection.PenulisContainer

class PerpusApp : Application(){

    lateinit var bukcontainer: AppContainer
    lateinit var katcontainer: AppKategoriContainer
    lateinit var penuliscontainer: AppPenulisContainer
    lateinit var penerbitcontainer: AppPenerbitContainer

    override fun onCreate() {
        super.onCreate()
        bukcontainer = BukuContainer()
        katcontainer = KategoriContainer()
        penuliscontainer = PenulisContainer()
        penerbitcontainer = PenerbitContainer()



    }

}