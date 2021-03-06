package com.example.restaurantapplicationgraduationproject.ui.mealDetail


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.restaurantapplicationgraduationproject.R
import com.example.restaurantapplicationgraduationproject.databinding.FragmentMealDetailBinding
import com.example.restaurantapplicationgraduationproject.model.entity.order.OrderAddRequest
import com.example.restaurantapplicationgraduationproject.ui.adapters.MealIngredientsAdapter
import com.example.restaurantapplicationgraduationproject.utils.Resource
import com.example.restaurantapplicationgraduationproject.utils.gone
import com.example.restaurantapplicationgraduationproject.utils.show
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MealDetailFragment : Fragment() {
    private val args: MealDetailFragmentArgs by navArgs()
    private val viewModel: MealDetailsViewModel by viewModels()
    private lateinit var _binding: FragmentMealDetailBinding
    private var adapter: MealIngredientsAdapter = MealIngredientsAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMealDetailBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ingredientsDummy = ArrayList<String>()
        ingredientsDummy.add("ingredient1")
        ingredientsDummy.add("ingredient2")
        initViews()
        initListener()
    }
    private fun initViews() {
        viewModel.getMealDetails(args.mealId).observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Log.e("Loading", "loading")
                    setLoading(true)
                }
                Resource.Status.SUCCESS -> {
                    setLoading(false)
                    val meal = it.data!!.data
                    viewModel.meal = meal
                    val options = RequestOptions().placeholder(R.mipmap.no_data)
                    Glide.with(_binding.mealImageView.context)
                        .applyDefaultRequestOptions(options)
                        .load(meal.image).into(_binding.mealImageView)
                    _binding.mealNameTextView.text = meal.name
                    _binding.ingredientsRecyclerView.layoutManager = LinearLayoutManager(context)
                    adapter.setIngredients(meal.ingredients)
                    _binding.ingredientsRecyclerView.adapter = adapter
                    _binding.priceTextView.text = meal.price
                }
                Resource.Status.ERROR -> {
                    setLoading(false)
                } } }) }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            _binding.progressBar.show()
            _binding.backButton.gone()
            _binding.mealImageView.gone()
            _binding.orderButton.gone()
            _binding.mealNameTextView.gone()
            _binding.totalLinearLayout.gone()

        } else {
            _binding.progressBar.gone()
            _binding.backButton.show()
            _binding.mealImageView.show()
            _binding.orderButton.show()
            _binding.mealNameTextView.show()
            _binding.totalLinearLayout.show()
        }
    }

    private fun initListener() {
        _binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        _binding.orderButton.setOnClickListener {
            val orderAddRequest = OrderAddRequest(args.restaurantId, args.mealId)
            viewModel.postOrder(orderAddRequest).observe(viewLifecycleOwner, {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        Log.e("Loading", "loading")
                        setLoading(true)
                        _binding.ingredientsRecyclerView.gone()
                    }
                    Resource.Status.SUCCESS -> {
                        setLoading(false)
                        _binding.ingredientsRecyclerView.show()
                        findNavController().navigate(MealDetailFragmentDirections.actionMealDetailFragmentToRestaurantListFragment())

                    }
                    Resource.Status.ERROR -> {
                        setLoading(false)
                        _binding.ingredientsRecyclerView.show()
                    } } }) } } }