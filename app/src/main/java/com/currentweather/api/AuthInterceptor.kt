package com.currentweather.api

import com.currentweather.data_sources.UnitsDataSource
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.*


/**
 * A {@see RequestInterceptor} that adds an auth token to requests
 */
class AuthInterceptor(private val accessToken: String, private val unitsDataSource: UnitsDataSource) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val originalHttpUrl: HttpUrl = original.url()

        val request: Request = original.newBuilder().url(
                originalHttpUrl.newBuilder()
                    .apply {
                        if(unitsDataSource.getAppUnit().key.isNotBlank()){
                            addQueryParameter("units", unitsDataSource.getAppUnit().key)
                        }
                        addQueryParameter("lang", Locale.getDefault().language)
                        addQueryParameter("appid", accessToken)
                    }

                    .build())
            .build()
        return chain.proceed(request)
    }
}
