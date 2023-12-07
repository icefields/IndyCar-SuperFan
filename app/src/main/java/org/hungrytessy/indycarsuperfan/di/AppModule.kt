package org.hungrytessy.indycarsuperfan.di

import android.content.Context
import com.prof18.rssparser.RssParser
import com.prof18.rssparser.RssParserBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.hungrytessy.indycarsuperfan.data.IndyRepositoryImpl
import org.hungrytessy.indycarsuperfan.data.remote.network.AssetsNetworkInterceptor
import org.hungrytessy.indycarsuperfan.data.remote.network.MainNetwork
import org.hungrytessy.indycarsuperfan.data.remote.network.MainNetwork.Companion.BASE_URL
import org.hungrytessy.indycarsuperfan.domain.repository.IndyRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // means this module will live as long as the application
object AppModule {

    @Provides
    fun provideInterceptor(@ApplicationContext appContext: Context): Interceptor =
        AssetsNetworkInterceptor(appContext)

    @Provides
    @Singleton
    fun provideRetrofit(interceptor: Interceptor): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideIndyApi(retrofit: Retrofit): MainNetwork = retrofit.create(MainNetwork::class.java)

//    @Provides
//    @Singleton
//    fun provideIndyRepository(api: MainNetwork): IndyRepository = IndyRepositoryImpl(api)
}
