package com.todo.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.todo.data.data_source.TodoDao
import com.todo.data.data_source.TodoDatabase
import com.todo.data.remote.ApiInterface
import com.todo.repository.api.TodoListRepository
import com.todo.repository.api.TodoListRepositoryImp
import com.todo.repository.local_database.TodoRepository
import com.todo.repository.local_database.TodoRepositoryImp
import com.todo.utils.ConstValue.BASE_URL
import com.todo.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Singleton
    @Provides
    fun provideTodoDatabase(app: Application): TodoDatabase{
        return Room.databaseBuilder(
            app,
            TodoDatabase::class.java,
            TodoDatabase.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideTodoRepository(db : TodoDatabase): TodoRepository{
        return TodoRepositoryImp(db.todoDao)
    }

    @Singleton
    @Provides
    fun provideShopRepo (
        api: ApiInterface
    ): TodoListRepository = TodoListRepositoryImp(api)

    @Singleton
    @Provides
    fun provideDispatcher() : DispatcherProvider = object : DispatcherProvider{
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }

    @Singleton
    @Provides
    fun OkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()
    @Singleton
    @Provides
    fun provideRetrofit(): ApiInterface = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(
            GsonBuilder()
                .setLenient()
                .create()))
        .baseUrl(BASE_URL)
        .client(OkHttpClient())
        .build()
        .create()

}