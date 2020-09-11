package com.example.shoppinglisttesting.ui.fragment

import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.pressBack
import androidx.test.filters.MediumTest
import com.example.shoppinglisttesting.getOrAwaitValue
import com.example.shoppinglisttesting.launchFragmentInHiltContainer
import com.example.shoppinglisttesting.other.Event
import com.example.shoppinglisttesting.other.Resource
import com.example.shoppinglisttesting.remote.response.ImageResponse
import com.example.shoppinglisttesting.viewmodel.ShoppingViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class AddShoppingItemFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    lateinit var viewModel: ShoppingViewModel

    @Before
    fun setup (){
        hiltRule.inject()
    }

    @Test
    fun pressBackButton_popBackStack(){
       // val testViewModel = ShoppingViewModel(FakeShoppingRepositoryAndroidTest())

        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingItemFragment> {

            Navigation.setViewNavController(requireView(),navController)
        }

        pressBack()
        verify(navController).popBackStack()


    }

}