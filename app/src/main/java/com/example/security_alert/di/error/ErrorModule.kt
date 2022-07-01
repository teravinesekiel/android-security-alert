package com.example.security_alert.di.error

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.security_alert.di.ViewModelKey
import com.example.security_alert.fragment.error.ErrorViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import kotlinx.coroutines.CoroutineDispatcher


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