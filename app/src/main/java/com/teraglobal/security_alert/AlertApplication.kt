package com.teraglobal.security_alert

import androidx.appcompat.app.AppCompatDelegate
import com.teraglobal.security_alert.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber


/*
 * security_alert
 *
 * Created by Esekiel Surbakti on 30/06/22
 */

class AlertApplication : DaggerApplication() {


    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.factory().create(applicationContext)
    }
}