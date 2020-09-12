package com.example.shoppinglisttesting.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.example.shoppinglisttesting.R
import com.example.shoppinglisttesting.other.Status
import com.example.shoppinglisttesting.viewmodel.ShoppingViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_shopping_item.*

@AndroidEntryPoint
class AddShoppingItemFragment (
   private val glide : RequestManager
) : Fragment(R.layout.fragment_add_shopping_item) {

    lateinit var viewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)

//        subscribeToObserver()
//
//        btnAddShoppingItem.setOnClickListener {
//            viewModel.insertShoppingItem(
//                etShoppingItemName.text.toString(),
//                etShoppingItemAmount.text.toString(),
//                etShoppingItemPrice.text.toString()
//            )
//        }

        ivShoppingImage.setOnClickListener {
            findNavController().navigate(AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment())
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.setCurImageUrl("")
                findNavController().popBackStack()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

//   private fun subscribeToObserver(){
//      viewModel.curImageUrl.observe(viewLifecycleOwner, Observer {
//        glide.load(it).into(ivShoppingImage)
//      })
//       viewModel.insertShoppingItemStatus.observe(viewLifecycleOwner, Observer {
//           it?.getContentIfNotHandled()?.let {result->
//               when (result.status){
//                   Status.SUCCESS->{
//                       Snackbar.make(
//                           requireActivity().rootLayout,
//                           "Add Shopping Item",
//                       Snackbar.LENGTH_LONG
//                           ).show()
//                   }
//                   Status.ERROR->{
//                       Snackbar.make(
//                           requireActivity().rootLayout,
//                           result.message?:"Add Shopping Item",
//                           Snackbar.LENGTH_LONG
//                       ).show()                   }
//                   Status.LOADING->{
//                       /* NO-OP*/
//                   }
//               }
//
//           }
//       })
//    }
}