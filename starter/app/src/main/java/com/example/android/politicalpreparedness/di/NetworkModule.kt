package com.example.android.politicalpreparedness.di

import com.example.android.politicalpreparedness.network.ApiProvider
import com.example.android.politicalpreparedness.network.UrlProvider
import com.example.android.politicalpreparedness.network.service.CivicService
import com.example.android.politicalpreparedness.network.service.CountryService
import com.example.android.politicalpreparedness.network.service.HeaderProvider
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

inline fun <reified T> provideWebService(retrofit: Retrofit): T =
    retrofit.create(T::class.java)

internal val serviceModule = module {
    single { provideWebService<CivicService>(get(named(NetworkQualifier.CIVIC_RETROFIT))) }
    single { provideWebService<CountryService>(get(named(NetworkQualifier.COUNTRY_RETROFIT))) }
}

internal val retrofitModule = module {
    single(named(NetworkQualifier.CIVIC_RETROFIT)) {
        provideRetrofit(
            UrlProvider.baseCivicUrl,
            get(named(NetworkQualifier.CIVIC_CLIENT)),
        )
    }
    single(named(NetworkQualifier.COUNTRY_RETROFIT)) {
        provideRetrofit(
            UrlProvider.baseCountryUrl,
            get(named(NetworkQualifier.COUNTRY_CLIENT)),
        )
    }
}

internal val clientModule = module {
    single(named(NetworkQualifier.CIVIC_CLIENT)) {
        providerCivicOkHttpClient()
    }
    single(named(NetworkQualifier.COUNTRY_CLIENT)) {
        providerCountryOkHttpClient()
    }
}

val networkModule = serviceModule + retrofitModule + clientModule

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private fun providerCivicOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor { chain ->
        val original = chain.request()
        val url = original
            .url
            .newBuilder()
            .addQueryParameter(HeaderProvider.KEY, ApiProvider.civicApiKey)
            .build()
        val request = original
            .newBuilder()
            .url(url)
            .build()
        chain.proceed(request)
    }.addInterceptor(
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    )
    .build()

private fun providerCountryOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor { chain ->
        val original = chain.request()
        val url = original
            .url
            .newBuilder()
            .build()
        val request = original
            .newBuilder()
            .url(url)
            .build()
        chain.proceed(request)
    }
    .build()

private fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()