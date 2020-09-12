package com.example.shoppinglisttesting.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.shoppinglisttesting.R
import com.example.shoppinglisttesting.data.local.ShoppingDao
import com.example.shoppinglisttesting.data.local.ShoppingItemDataBase
import com.example.shoppinglisttesting.other.Constants.BASE_URL
import com.example.shoppinglisttesting.other.Constants.SHOPPINGITEM_DATABASE_NAME
import com.example.shoppinglisttesting.remote.PixabayAPI
import com.example.shoppinglisttesting.repositories.DefaultShoppingRepository
import com.example.shoppinglisttesting.repositories.ShoppingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun proviedshoppingDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(context, ShoppingItemDataBase::class.java, SHOPPINGITEM_DATABASE_NAME)
        .build()

    @Singleton
    @Provides
    fun proviedShoppingDao(
        dataBase: ShoppingItemDataBase
    ) = dataBase.shoppingDao()

    @Singleton
    @Provides
    fun proviedGlide ( @ApplicationContext context: Context
    )=Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)

    )

    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(
        dao: ShoppingDao,
        api: PixabayAPI
    ) = DefaultShoppingRepository(api, dao) as ShoppingRepository

    @Singleton
    @Provides
    fun proviedPixabayApi(): PixabayAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixabayAPI::class.java)
    }
}