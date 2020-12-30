package com.example.taipeizooinfo.di

import android.content.Context
import com.example.taipeizooinfo.data.cache.TaipeiZooDatabase
import com.example.taipeizooinfo.data.remote.ZooApi
import com.example.taipeizooinfo.data.util.DataConstants
import com.example.taipeizooinfo.repository.ZooRepository
import com.example.taipeizooinfo.repository.ZooRepositoryImp
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindZooRepository(repository: ZooRepositoryImp): ZooRepository

    companion object{
        @Singleton
        @Provides
        fun provideDatabase(@ApplicationContext context: Context) =
            TaipeiZooDatabase.getInstance(context)



        @Provides
        fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
            val cacheSize = 10 * 1024 * 1024
            val cache = Cache(context.cacheDir, cacheSize.toLong())

            return OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build()
        }

        @Provides
        fun provideRetrofit(client: OkHttpClient): Retrofit =
            Retrofit.Builder()
                .baseUrl(DataConstants.BASE_URL)
                .client(client)
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().create()
                    )
                )
                .build()

        @Singleton
        @Provides
        fun provideSectionsApi(retrofit: Retrofit): ZooApi =
            retrofit.create(ZooApi::class.java)
    }
}