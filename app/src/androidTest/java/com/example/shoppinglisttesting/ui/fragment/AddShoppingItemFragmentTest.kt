package com.example.shoppinglisttesting.ui.fragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.shoppinglisttesting.R
import com.example.shoppinglisttesting.data.local.ShoppingItem
import com.example.shoppinglisttesting.getOrAwaitValue
import com.example.shoppinglisttesting.launchFragmentInHiltContainer
import com.example.shoppinglisttesting.other.Event
import com.example.shoppinglisttesting.other.Resource
import com.example.shoppinglisttesting.remote.response.ImageResponse
import com.example.shoppinglisttesting.repositories.FakeShoppingRepository
import com.example.shoppinglisttesting.viewmodel.ShoppingViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.android.synthetic.main.fragment_add_shopping_item.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class AddShoppingItemFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactory

    @Before
    fun setup (){
        hiltRule.inject()
    }



//    @Test
//    fun clickInsertIntoDb_shoppingItemInsertedIntoDb(){
//        val testViewModel = ShoppingViewModel(FakeShoppingRepository())
//        launchFragmentInHiltContainer<AddShoppingItemFragment>(fragmentFactory = fragmentFactory ) {
//            viewModel = testViewModel
//        }
//        onView(withId(R.id.etShoppingItemName)).perform(replaceText("Shopping item"))
//        onView(withId(R.id.etShoppingItemAmount)).perform(replaceText("5"))
//        onView(withId(R.id.etShoppingItemPrice)).perform(replaceText("5.5"))
//        onView(withId(R.id.btnAddShoppingItem)).perform(click())
//        assertThat(testViewModel.shoppingItems.getOrAwaitValue ()).contains(ShoppingItem("Shopping item", 5,5.5f,""))
//
//
//    }


    @Test
    fun pressBackButton_popBackStack(){
       // val testViewModel = ShoppingViewModel(FakeShoppingRepositoryAndroidTest())
        val imageUrl = ""
        val testViewModel = ShoppingViewModel(FakeShoppingRepository())
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingItemFragment> {

            Navigation.setViewNavController(requireView(),navController)
        }

        pressBack()
        testViewModel.setCurImageUrl("")
        verify(navController).popBackStack()
        assertThat(testViewModel.curImageUrl.getOrAwaitValue()).isEqualTo("")


    }

}