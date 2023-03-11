package com.example.recipesearch

import android.app.Application
import androidx.room.Room
import com.example.recipesearch.retrofit.RetrofitApi
import com.example.recipesearch.room.SearchDatabase
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
object RecipeModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(logging).build()
    }
    @Provides
    @Singleton
    fun provideRetrofit(getOkHttpInstance:OkHttpClient): RetrofitApi {
        return Retrofit.Builder()
            .client(getOkHttpInstance)
            .baseUrl("https://api.edamam.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitApi::class.java)
    }
    @Provides
    @Singleton
    fun ProvideDatabase(context: Application) =
        Room.databaseBuilder(context, SearchDatabase::class.java,"search_database")
        .fallbackToDestructiveMigration().createFromAsset("database/search_table.db")
        .build()
    @Provides
    fun ProvideDao(database: SearchDatabase) = database.getDao()
}