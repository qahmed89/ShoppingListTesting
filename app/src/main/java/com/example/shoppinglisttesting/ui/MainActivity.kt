package com.example.shoppinglisttesting.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shoppinglisttesting.R
import com.example.shoppinglisttesting.ui.fragment.ShoppingFragmentFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.fragmentFactory = fragmentFactory

    }
}