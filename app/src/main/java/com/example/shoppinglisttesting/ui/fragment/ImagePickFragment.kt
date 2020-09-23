package com.example.shoppinglisttesting.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shoppinglisttesting.R
import com.example.shoppinglisttesting.adapters.ImageAdapter
import com.example.shoppinglisttesting.other.Constants.GRID_SPAN_COUNT
import com.example.shoppinglisttesting.other.Constants.SEARCH_TIME_DELAY
import com.example.shoppinglisttesting.other.Status
import com.example.shoppinglisttesting.viewmodel.ShoppingViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_image_pick.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ImagePickFragment @Inject constructor(val imageAdapter: ImageAdapter) :
    Fragment(R.layout.fragment_image_pick) {

    lateinit var viewModels: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModels = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
        setupRecyclerView()
        subscribeToObservers()

        imageAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModels.setCurImageUrl(it)
        }
        var job: Job? = null
        etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_TIME_DELAY)
                editable?.let {
                    viewModels.searchForImage(editable.toString())
                }
            }
        }
    }

    fun subscribeToObservers() {
        viewModels.images.observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        val urls = result.data?.hits?.map { imageResult -> imageResult.previewURL }
                        imageAdapter.image = urls ?: listOf()
                        progressBar.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        Snackbar.make(
                            requireActivity().rootLayout,
                            result.message ?: "An unknown error occured.",
                            Snackbar.LENGTH_LONG
                        ).show()
                        progressBar.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })

    }

    private fun setupRecyclerView() {
        rvImages.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(requireContext(), GRID_SPAN_COUNT)
        }
    }

}