package com.teraglobal.security_alert.di.unauthorized_detail

import androidx.lifecycle.ViewModel
import com.teraglobal.security_alert.di.ViewModelKey
import com.teraglobal.security_alert.fragment.unauthorized_detail.UnauthorizedDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


/*
 * security_alert
 *
 * Created by Esekiel Surbakti on 01/07/22
 */

@Module
abstract class UnauthorizedDetailModule {

    @Binds
    @IntoMap
    @ViewModelKey(UnauthorizedDetailViewModel::class)
    abstract fun bindsViewModel(viewModel: UnauthorizedDetailViewModel): ViewModel
}