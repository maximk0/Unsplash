package com.example.unsplash.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.unsplash.TAG_AUTH
import com.example.unsplash.api.BASE_URL
import com.example.unsplash.api.UnsplashApi
import com.example.unsplash.data.SharedPrefsRepository
import com.example.unsplash.data.UnsplashRemoteMediator
import com.example.unsplash.data.auth.AuthRepository
import com.example.unsplash.data.auth.TokenStorage
import com.example.unsplash.data.network.AuthorizationFailedInterceptor
import com.example.unsplash.data.network.AuthorizationInterceptor
import com.example.unsplash.data.network.UnsplashApiRepository
import com.example.unsplash.data.room.AppDatabase
import com.example.unsplash.data.room.UnsplashDaoRepository
import com.example.unsplash.data.room.UnsplashPhotosDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.openid.appauth.AuthorizationService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "db"
        ).build()

    @Provides
    fun provideApi(
        @ApplicationContext context: Context,
        authRepository: AuthRepository
    ): UnsplashApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addNetworkInterceptor(
                    HttpLoggingInterceptor {
                        Log.d(TAG_AUTH, "AppModule logging interceptor: $it")
                    }
                        .setLevel(HttpLoggingInterceptor.Level.BODY)
                )
                .addNetworkInterceptor(AuthorizationInterceptor())
                .addNetworkInterceptor(
                    AuthorizationFailedInterceptor(
                        AuthorizationService(context),
                        TokenStorage,
                        authRepository
                    )
                )
                .build()
        )
        .build()
        .create(UnsplashApi::class.java)

    @Singleton
    @Provides
    fun provideUnsplashDao(db: AppDatabase) = db.getUnsplashPhotosEntity()

    @Singleton
    @Provides
    fun provideDaoRepository(dao: UnsplashPhotosDao) = UnsplashDaoRepository(dao)

    @Singleton
    @Provides
    fun provideSharedPrefRepository(@ApplicationContext context: Context) =
        SharedPrefsRepository(context)

    @Singleton
    @Provides
    fun provideAuthRepository() =
        AuthRepository()

    @Singleton
    @Provides
    fun provideApiRepository(
        unsplashApi: UnsplashApi
    ) =
        UnsplashApiRepository(unsplashApi)

    @Singleton
    @Provides
    fun provideUnsplashRemoteMediator(
        apiRepository: UnsplashApiRepository,
        daoRepository: UnsplashDaoRepository
    ) = UnsplashRemoteMediator(apiRepository, daoRepository)
}