package com.teraglobal.security_alert.di.error

import androidx.lifecycle.ViewModel
import com.teraglobal.security_alert.di.ViewModelKey
import com.teraglobal.security_alert.fragment.error.ErrorViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


/*
 * security_alert
 *
 * Created by Esekiel Surbakti on 01/07/22
 */

@Module
abstract class ErrorModule {

    @Binds
    @IntoMap
    @ViewModelKey(ErrorViewModel::class)
    abstract fun bindsViewModel(viewModel: ErrorViewModel): ViewModel
}