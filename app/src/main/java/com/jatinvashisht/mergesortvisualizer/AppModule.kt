package com.jatinvashisht.mergesortvisualizer

import com.jatinvashisht.mergesortvisualizer.util.MergesortHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesMergesortHelper(): MergesortHelper = MergesortHelper()
}