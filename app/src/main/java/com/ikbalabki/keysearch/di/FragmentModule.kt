package com.ikbalabki.keysearch.di

import com.ikbalabki.keysearch.ui.CityResultAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent


@Module
@InstallIn(FragmentComponent::class)
class FragmentModule {
    @Provides
    fun provideAdapter(): CityResultAdapter {
        return CityResultAdapter()
    }
}