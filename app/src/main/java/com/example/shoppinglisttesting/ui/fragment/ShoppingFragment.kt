package com.example.shoppinglisttesting.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglisttesting.R
import com.example.shoppinglisttesting.adapters.ShoppingItemAdapter
import com.example.shoppinglisttesting.viewmodel.ShoppingViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_shopping.*
import javax.inject.Inject

@AndroidEntryPoint
class ShoppingFragment @Inject constructor(
    val shoppingItemAdapter: ShoppingItemAdapter,
    var viewModels: ShoppingViewModel? = null
) :
    Fragment(R.layout.fragment_shopping) {

    //lateinit var viewModels: ShoppingViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModels =
            viewModels ?: ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
        subscribeToObserver()
        setupRecycleView()
        fabAddShoppingItem.setOnClickListener {
            findNavController().navigate(
                ShoppingFragmentDirections.actionShoppingFragmentToAddShoppingItemFragment()
            )
        }
    }

    private val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, LEFT or RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ) = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val pos = viewHolder.adapterPosition
            val item = shoppingItemAdapter.shoppingItems[pos]
            viewModels?.deleteShoppingItem(item)
            Snackbar.make(requireView(), "Succeeful delete item", Snackbar.LENGTH_LONG).apply {
                setAction("undo") {
                    viewModels?.insertShoppingItemIntoDb(item)
                }
                show()
            }
        }

    }

    private fun subscribeToObserver() {
        viewModels?.shoppingItems?.observe(viewLifecycleOwner, {
            shoppingItemAdapter.shoppingItems = it
        })
        viewModels?.totalPrice?.observe(viewLifecycleOwner, {
            val price = it ?: 0f
            val priceText = "Total Price : $price$"
            tvShoppingItemPrice.text = priceText
        })

    }

    private fun setupRecycleView() {
        rvShoppingItems.apply {
            adapter = shoppingItemAdapter
            layoutManager = LinearLayoutManager(requireContext())
            ItemTouchHelper(itemTouchCallback).attachToRecyclerView(this)
        }
    }
}