package com.example.shoppinglisttesting.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.shoppinglisttesting.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class ShoppingDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var dataBase: ShoppingItemDataBase
    lateinit var dao: ShoppingDao


    @Before
    fun setup() {

        hiltRule.inject()
        dao = dataBase.shoppingDao()
    }

    @After
    fun teardown() {
        dataBase.close()
    }

    @Test
    fun insertShoppingItem() = runBlockingTest {
        val shoppingitem = ShoppingItem("apple", 1, 1f, "", 1)
        dao.insertShoppingItem(shoppingitem)
        val allShoppingItems = dao.observeAllShoppingItem().getOrAwaitValue()
        assertThat(allShoppingItems).contains(shoppingitem)

    }

    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val shoppingitem = ShoppingItem("apple", 1, 1f, "", 1)
        dao.insertShoppingItem(shoppingitem)
        dao.deleteShoppingItem(shoppingitem)
        val allShoppingItems = dao.observeAllShoppingItem().getOrAwaitValue()
        assertThat(allShoppingItems).doesNotContain(shoppingitem)
    }

    @Test
    fun observeTotalPriceSum() = runBlockingTest {
        val shoppingitem1 = ShoppingItem("apple", 5, 10f, "", 1)
        val shoppingitem2 = ShoppingItem("apple", 1, 100f, "", 2)
        val shoppingitem3 = ShoppingItem("apple", 10, 5f, "", 3)
        dao.insertShoppingItem(shoppingitem1)
        dao.insertShoppingItem(shoppingitem2)
        dao.insertShoppingItem(shoppingitem3)
        val totalPriceSum = dao.observeTotalPrice().getOrAwaitValue()
        assertThat(totalPriceSum).isEqualTo(5 * 10f + 1 * 100f + 10 * 5f)

    }
}