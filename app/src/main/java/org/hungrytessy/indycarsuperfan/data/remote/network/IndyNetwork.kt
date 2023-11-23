package org.hungrytessy.indycarsuperfan.data.remote.network

import android.content.Context
import okhttp3.OkHttpClient
import org.hungrytessy.indycarsuperfan.data.remote.network.MainNetwork.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object IndyNetwork {
    private var service: MainNetwork? = null

    fun getNetworkService(context: Context): MainNetwork {
        if (service == null) {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(AssetsNetworkInterceptor(context))
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            service = retrofit.create(MainNetwork::class.java)
        }
        return service!!
    }
}
