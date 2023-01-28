package com.nishantp.payback.di

import android.app.Application
import androidx.room.Room
import com.nishantp.payback.BuildConfig
import com.nishantp.payback.constants.AppConstants
import com.nishantp.payback.main.data.localdb.ImageDao
import com.nishantp.payback.main.data.localdb.PaybackDatabase
import com.nishantp.payback.main.data.remote.SearchImagesApi
import com.nishantp.payback.main.data.repository.ImageRepositoryImpl
import com.nishantp.payback.main.domain.repository.ImageRepository
import com.nishantp.payback.main.domain.usecase.GetImageDetailsUseCase
import com.nishantp.payback.main.domain.usecase.SearchImagesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
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
    fun providesShortlyDatabase(app: Application): PaybackDatabase {
        return Room.databaseBuilder(
            app, PaybackDatabase::class.java, AppConstants.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(interceptor: Interceptor): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).client(
                OkHttpClient.Builder().addInterceptor(interceptor = interceptor).build()
            )
    }

    @Singleton
    @Provides
    fun provideInterceptor(): Interceptor {
        return if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(level = HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(level = HttpLoggingInterceptor.Level.NONE)
        }
    }

    @Singleton
    @Provides
    fun provideSearchImagesApiEndPoint(retrofit: Retrofit.Builder): SearchImagesApi {
        return retrofit.build().create(SearchImagesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideImageDao(paybackDatabase: PaybackDatabase): ImageDao {
        return paybackDatabase.imageDao
    }

    @Singleton
    @Provides
    fun provideImageRepository(
        searchImagesApi: SearchImagesApi, imageDao: ImageDao
    ): ImageRepository {
        return ImageRepositoryImpl(searchImagesApi = searchImagesApi, imageDao = imageDao)
    }

    @Provides
    @Singleton
    fun provideSearchImageUseCase(imageRepository: ImageRepository): SearchImagesUseCase {
        return SearchImagesUseCase(repository = imageRepository)
    }

    @Provides
    @Singleton
    fun provideGetImageDetailsUseCase(imageRepository: ImageRepository): GetImageDetailsUseCase {
        return GetImageDetailsUseCase(repository = imageRepository)
    }
}