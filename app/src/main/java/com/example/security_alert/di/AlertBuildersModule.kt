package com.example.security_alert.di

import com.example.security_alert.di.error.ErrorModule
import com.example.security_alert.di.error.ErrorScope
import com.example.security_alert.di.unauthorized.UnauthorizedModule
import com.example.security_alert.di.unauthorized.UnauthorizedScope
import com.example.security_alert.di.unauthorized_detail.UnauthorizedDetailModule
import com.example.security_alert.di.unauthorized_detail.UnauthorizedDetailScope
import com.example.security_alert.fragment.error.ErrorFragment
import com.example.security_alert.fragment.unauthorized.UnauthorizedFragment
import com.example.security_alert.fragment.unauthorized_detail.UnauthorizedDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


/*
 * security_alert
 *
 * Created by Esekiel Surbakti on 01/07/22
 */

@Module
abstract class AlertBuildersModule {

    @UnauthorizedScope
    @ContributesAndroidInjector(
        modules = [
            UnauthorizedModule::class
        ]
    )
    internal abstract fun unauthorizedFragment(): UnauthorizedFragment

    @ErrorScope
    @ContributesAndroidInjector(
        modules = [
            ErrorModule::class
        ]
    )
    internal abstract fun errorFragment(): ErrorFragment

    @UnauthorizedDetailScope
    @ContributesAndroidInjector(
        modules = [
            UnauthorizedDetailModule::class
        ]
    )
    internal abstract fun unauthorizedDetailFragment(): UnauthorizedDetailFragment
}