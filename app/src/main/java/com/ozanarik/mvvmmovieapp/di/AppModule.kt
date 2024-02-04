package com.ozanarik.mvvmmovieapp.di

import com.ozanarik.mvvmmovieapp.business.remote.MovieApi
import com.ozanarik.mvvmmovieapp.business.remote.ShowApi
import com.ozanarik.mvvmmovieapp.business.repository.MovieRepository
import com.ozanarik.mvvmmovieapp.business.repository.ShowsRepository
import com.ozanarik.mvvmmovieapp.utils.CONSTANTS.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideOkHttp():OkHttpClient{
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideBASEURL()=BASE_URL

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit):MovieApi{
        return retrofit.create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(movieApi: MovieApi):MovieRepository{
        return MovieRepository(movieApi)
    }

    @Provides
    @Singleton
    fun provideShowsApi(retrofit: Retrofit):ShowApi{
        return retrofit.create(ShowApi::class.java)
    }

    @Provides
    @Singleton
    fun provideShowsRepository(showApi: ShowApi):ShowsRepository{
        return ShowsRepository(showApi)
    }

}