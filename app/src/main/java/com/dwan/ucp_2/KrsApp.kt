package com.dwan.ucp_2

import android.app.Application
import com.dwan.ucp_2.dependenciesinjection.ContainerApp
import com.dwan.ucp_2.dependenciesinjection.InterfaceContainerApp

class KrsApp: Application() {
    lateinit var containerApp: ContainerApp

    override fun onCreate() {
        super.onCreate()

        containerApp = ContainerApp(this)
    }
}