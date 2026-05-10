package com.example.coffeeshop.activity

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.GridLayoutManager
import com.example.coffeeshop.R
import com.example.coffeeshop.adapter.OffersAdapter
import com.example.coffeeshop.adapter.PopularAdapter
import com.example.coffeeshop.databinding.ActivityMainBinding
import com.example.coffeeshop.viewmodel.MainViewModel

class MainActivity : BaseActivity() {

    private val viewModel = MainViewModel()

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bottomNavigation.background = null

        initPopular()
        initOffer()
        bottomMenu()
    }

    private fun bottomMenu() {

        // CART BUTTON
        binding.cartBtn.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        // BOTTOM NAVIGATION
        binding.bottomNavigation.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }

                R.id.nav_orders -> {
                    startActivity(Intent(this, OrdersActivity::class.java))
                    true
                }

                else -> false
            }
        }
    }

    //Popular 
    private fun initPopular() {

        binding.progressBarPopular.visibility = android.view.View.VISIBLE

        viewModel.popular.observe(this) {

            binding.recyclerViewPopular.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

            binding.recyclerViewPopular.adapter = PopularAdapter(it)

            binding.progressBarPopular.visibility = android.view.View.GONE
        }

        viewModel.loadPopular()
    }

    private fun initOffer() {

        binding.progressBarOffer.visibility = android.view.View.VISIBLE

        viewModel.offers.observe(this) {

            binding.recyclerViewOffer.layoutManager =
                GridLayoutManager(this, 2)

            binding.recyclerViewOffer.adapter = OffersAdapter(it)

            binding.progressBarOffer.visibility = android.view.View.GONE
        }

        viewModel.loadOffers()
    }
}