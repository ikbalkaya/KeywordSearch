package com.ikbalabki.keysearch.di

import android.content.Context
import com.ikbalabki.keysearch.CitySearchApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext


@Module
@InstallIn(ViewModelComponent::class)
class ViewModelModule {
    @Provides
    fun provideApp(@ApplicationContext context:Context): CitySearchApp{
       return context.applicationContext as CitySearchApp
    }
}