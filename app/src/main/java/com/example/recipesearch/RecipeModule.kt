package com.example.recipesearch

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.recipesearch.retrofit.RetrofitApi
import com.example.recipesearch.room.SearchDao
import com.example.recipesearch.room.SearchDatabase
import com.example.recipesearch.saveUi.SaveRepository
import com.example.recipesearch.searchUi.SearchRepository
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
    fun provideRetrofit(getOkHttpInstance:OkHttpClient) = Retrofit.Builder()
            .client(getOkHttpInstance)
            .baseUrl("https://api.edamam.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitApi::class.java)
    @Provides
    @Singleton
    fun provideDatabase(context: Application) =
        Room.databaseBuilder(context, SearchDatabase::class.java,"search_database")
        .fallbackToDestructiveMigration()
        .build()
    @Provides
    fun provideDao(database: SearchDatabase) = database.getDao()

    @Provides
    @Singleton
    fun provideSharedPreference(context: Application) = context.getSharedPreferences("recipe",Context.MODE_PRIVATE)

    @Provides
    fun provideSearchRepository(searchDao: SearchDao, prefs: SharedPreferences,retrofit: RetrofitApi) = SearchRepository(searchDao,prefs,retrofit)
    @Provides
    fun provideSaveRepository(searchDao: SearchDao) = SaveRepository(searchDao)
}