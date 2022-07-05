package com.teraglobal.security_alert.di

import android.content.Context
import com.teraglobal.security_alert.AlertApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


/*
 * security_alert
 *
 * Created by Esekiel Surbakti on 30/06/22
 */

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        AlertModule::class,
        AlertBuildersModule::class,
        ViewModelBuilder::class]
)

interface ApplicationComponent : AndroidInjector<AlertApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}