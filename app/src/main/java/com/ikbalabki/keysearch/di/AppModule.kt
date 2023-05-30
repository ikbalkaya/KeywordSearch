package com.ikbalabki.keysearch.di

import android.content.Context
import com.ikbalabki.keysearch.R
import com.ikbalabki.keysearch.data.CitiesRepo
import com.ikbalabki.keywordsearch.KeySearch
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideRepo(@ApplicationContext context:Context): CitiesRepo{
        val inputStream = context.resources.openRawResource(R.raw.cities)
        return CitiesRepo(KeySearch(),inputStream)
    }
}