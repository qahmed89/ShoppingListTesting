package com.example.shoppinglisttesting.ui.fragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.shoppinglisttesting.R
import com.example.shoppinglisttesting.adapters.ShoppingItemAdapter
import com.example.shoppinglisttesting.data.local.ShoppingItem
import com.example.shoppinglisttesting.getOrAwaitValue
import com.example.shoppinglisttesting.launchFragmentInHiltContainer
import com.example.shoppinglisttesting.viewmodel.ShoppingViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ShoppingFragmentTest {


    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: TestShoppingFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }


    @Test
    fun swipShoppingItem_deleteItemInDb() {
        val shoppingItem = ShoppingItem("TEST", 1, 1f, "TEST")
        var testViewModel: ShoppingViewModel? = null
        launchFragmentInHiltContainer<ShoppingFragment>(fragmentFactory = fragmentFactory) {
            testViewModel = viewModels
            viewModels?.insertShoppingItemIntoDb(shoppingItem)
        }
        onView(withId(R.id.rvShoppingItems)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ShoppingItemAdapter.ShoppingItemViewHolder>(
                0,
                swipeLeft()
            )

        )
        assertThat(testViewModel?.shoppingItems?.getOrAwaitValue()).isEmpty()
    }

    @Test
    fun clickAddShoppingItemButton_navigateToAddShoppingItemFragment() {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<ShoppingFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.fabAddShoppingItem)).perform(click())

        verify(navController).navigate(
            ShoppingFragmentDirections.actionShoppingFragmentToAddShoppingItemFragment()
        )
    }
}
