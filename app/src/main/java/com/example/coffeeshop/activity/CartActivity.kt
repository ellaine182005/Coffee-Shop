package com.example.coffeeshop.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeshop.adapter.CartAdapter
import com.example.coffeeshop.databinding.ActivityCartBinding
import com.example.coffeeshop.helper.ChangeNumberItemsListener
import com.example.coffeeshop.helper.ManagmentCart

class CartActivity : BaseActivity() {
   //Cart
    private lateinit var management: ManagmentCart
    private var tax: Double = 0.0

    private val binding: ActivityCartBinding by lazy {
        ActivityCartBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        management = ManagmentCart(this)

        setVariable()
        initCartList()
        calculateCart()

        // ✅ CHECKOUT BUTTON
        binding.checkoutBtn.setOnClickListener {

            val items = management.getListCart()

            val itemsText = items.joinToString(", ") {
                "${it.title} x${it.numberInCart}"
            }

            val subtotal = management.getTotalFee()
            val delivery = 50.0
            val total = subtotal + tax + delivery

            val intent = Intent(this, CheckoutActivity::class.java)
            intent.putExtra("items", itemsText)
            intent.putExtra("total", total)

            startActivity(intent)
        }
    }

    private fun initCartList() {
        with(binding) {
            rvCartView.layoutManager =
                LinearLayoutManager(
                    this@CartActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )

            rvCartView.adapter = CartAdapter(
                management.getListCart(),
                this@CartActivity,
                object : ChangeNumberItemsListener {
                    override fun onChanged() {
                        calculateCart()
                    }
                }
            )
        }
    }

    private fun setVariable() {
        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun calculateCart() {

        val percentTax = 0.02
        val delivery = 50.0

        val subtotal = management.getTotalFee()
        tax = kotlin.math.round(subtotal * percentTax * 100) / 100.0

        val total = kotlin.math.round((subtotal + tax + delivery) * 100) / 100
        val itemTotal = kotlin.math.round(subtotal * 100) / 100

        with(binding) {
            subTotalPriceTxt.text = "₱$itemTotal"
            totalTaxPriceTxt.text = "₱$tax"
            deliveryPriceTxt.text = "₱$delivery"
            totalPriceTxt.text = "₱$total"
        }
    }
}