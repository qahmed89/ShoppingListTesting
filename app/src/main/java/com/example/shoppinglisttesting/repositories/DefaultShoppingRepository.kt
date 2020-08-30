package com.example.shoppinglisttesting.repositories

import androidx.lifecycle.LiveData
import com.example.shoppinglisttesting.data.local.ShoppingDao
import com.example.shoppinglisttesting.data.local.ShoppingItem
import com.example.shoppinglisttesting.other.Resource
import com.example.shoppinglisttesting.remote.PixabayAPI
import com.example.shoppinglisttesting.remote.response.ImageResponse
import javax.inject.Inject

class DefaultShoppingRepository @Inject constructor(
    private val pixabayAPI: PixabayAPI,
    private val shoppingDao: ShoppingDao
) : ShoppingRepository {
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        return shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        return shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItem()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return try {
            val response = pixabayAPI.searchForImage(imageQuery)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occured", null)

            } else {
                Resource.error("An unknown error occured", null)
            }
        } catch (e: Exception) {
            Resource.error("Couldnt reach to the  server, Check Internet Connection",null)
        }
    }


}