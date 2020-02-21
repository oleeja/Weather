package com.currentweather.api

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

private fun <T> provideService(okhttpClient: OkHttpClient,
                               converterFactory: GsonConverterFactory, clazz: Class<T>): T {
    return createRetrofit(okhttpClient, converterFactory).create(clazz)
}

private fun providePrivateOkHttpClient(): OkHttpClient {
    return OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
        .addInterceptor(AuthInterceptor("98fa0b9cfe122d8fb6a87e539fcf4876")).build()
}

private fun provideGsonConverterFactory(): GsonConverterFactory =
    GsonConverterFactory.create(Gson())

fun provideWeatherService()
        = provideService(providePrivateOkHttpClient(), provideGsonConverterFactory(), WeatherService::class.java)