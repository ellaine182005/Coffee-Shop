package com.example.coffeeshop.activity

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeshop.adapter.OrdersAdapter
import com.example.coffeeshop.databinding.ActivityOrdersBinding
import com.example.coffeeshop.model.OrderModel
import com.example.coffeeshop.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrdersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrdersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadOrders()
    }

    private fun loadOrders() {

        // ✅ GET USER FROM LOGIN SESSION
        val sharedPref = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val userName = sharedPref.getString("name", "") ?: ""

        if (userName.isEmpty()) {
            Toast.makeText(this, "No user found", Toast.LENGTH_SHORT).show()
            return
        }

        // ✅ FETCH ORDERS FROM SERVER
        RetrofitClient.instance.getOrdersByUser(userName)
            .enqueue(object : Callback<List<OrderModel>> {

                override fun onResponse(
                    call: Call<List<OrderModel>>,
                    response: Response<List<OrderModel>>
                ) {

                    if (response.isSuccessful) {

                        val orders = response.body()

                        if (orders.isNullOrEmpty()) {
                            Toast.makeText(this@OrdersActivity, "Walay orders pa", Toast.LENGTH_SHORT).show()
                            return
                        }

                        binding.ordersRecycler.layoutManager =
                            LinearLayoutManager(this@OrdersActivity)

                        binding.ordersRecycler.adapter =
                            OrdersAdapter(orders)

                    } else {
                        Toast.makeText(this@OrdersActivity, "Server error", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<OrderModel>>, t: Throwable) {
                    Toast.makeText(this@OrdersActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}