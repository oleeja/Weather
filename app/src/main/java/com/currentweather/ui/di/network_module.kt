package com.currentweather.ui.di

import com.currentweather.api.AuthInterceptor
import com.currentweather.api.WeatherService
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitNetworkModule = module{
    single { createRetrofit(get(), get()).create(WeatherService::class.java) }

    single { GsonConverterFactory.create(Gson()) }

    single { OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        .addInterceptor(AuthInterceptor("98fa0b9cfe122d8fb6a87e539fcf4876", get())).build() }
}

private fun createRetrofit(
    okhttpClient: OkHttpClient,
    converterFactory: GsonConverterFactory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(WeatherService.ENDPOINT)
        .client(okhttpClient)
        .addConverterFactory(converterFactory)
        .build()
}