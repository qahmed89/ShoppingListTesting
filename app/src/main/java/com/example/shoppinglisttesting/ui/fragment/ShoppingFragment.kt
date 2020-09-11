package com.example.shoppinglisttesting.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.shoppinglisttesting.R
import com.example.shoppinglisttesting.other.Event
import com.example.shoppinglisttesting.viewmodel.ShoppingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_shopping.*

@AndroidEntryPoint
class ShoppingFragment : Fragment(R.layout.fragment_shopping) {

     val viewModels: ShoppingViewModel by activityViewModels()
    // lateinit var viewModels: ShoppingViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
     //   viewModels = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
        fabAddShoppingItem.setOnClickListener {
            findNavController().navigate(ShoppingFragmentDirections.actionShoppingFragmentToAddShoppingItemFragment())
        }
    }
}