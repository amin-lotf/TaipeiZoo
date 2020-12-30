package com.example.taipeizooinfo.di



import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.taipeizooinfo.presentation.util.GlideManagerImpl
import com.example.taipeizooinfo.presentation.util.GlideManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {



    @Singleton
    @Provides
    fun provideGlide(@ApplicationContext context: Context):RequestManager=
        Glide.with(context)


    @Singleton
    @Provides
    fun provideGlideLoader(requestManager: RequestManager):GlideManager =
        GlideManagerImpl(requestManager)

}