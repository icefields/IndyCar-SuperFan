package org.hungrytessy.indycarsuperfan.network

import android.content.Context
import okhttp3.OkHttpClient
import org.hungrytessy.indycarsuperfan.data.models.Drivers
import org.hungrytessy.indycarsuperfan.data.models.Seasons
import org.hungrytessy.indycarsuperfan.data.models.Venues
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object IndyNetwork {
    private var service: MainNetwork? = null

    fun getNetworkService(context: Context): MainNetwork {
        if (service == null) {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(AssetsNetworkInterceptor(context))
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("http://localhost/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            service = retrofit.create(MainNetwork::class.java)
        }
        return service!!
    }
}
