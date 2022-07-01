package com.example.security_alert.di.unauthorized

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.security_alert.di.ViewModelKey
import com.example.security_alert.fragment.unauthorized.UnauthorizedViewModel
import com.example.security_alert.fragment.unauthorized.datasource.UnauthorizedApi
import com.example.security_alert.fragment.unauthorized.datasource.UnauthorizedDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit


/*
 * security_alert
 *
 * Created by Esekiel Surbakti on 01/07/22
 */

@Module
abstract class UnauthorizedModule {

    companion object {

        @UnauthorizedScope
        @Provides
        internal fun providesApi(retrofit: Retrofit): UnauthorizedApi {
            return retrofit.create(UnauthorizedApi::class.java)
        }

        @UnauthorizedScope
        @Provides
        internal fun providesDataSource(
            api: UnauthorizedApi,
            ioDispatcher: CoroutineDispatcher
        ): UnauthorizedDataSource {
            return UnauthorizedDataSource(api, ioDispatcher)
        }

    }

    @Binds
    @IntoMap
    @ViewModelKey(UnauthorizedViewModel::class)
    abstract fun bindsViewModel(viewModel: UnauthorizedViewModel): ViewModel
}