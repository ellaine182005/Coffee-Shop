package com.example.coffeeshop.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeeshop.databinding.ActivityCheckoutBinding
import com.example.coffeeshop.model.LoginResponse
import com.example.coffeeshop.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckoutActivity : AppCompatActivity() {
    //Checkout
    private lateinit var binding: ActivityCheckoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // GET DATA FROM CART
        val items = intent.getStringExtra("items") ?: "No items"
        val total = intent.getDoubleExtra("total", 0.0)

        // DISPLAY
        binding.itemsTxt.text = items
        binding.totalTxt.text = "Total: ₱$total"

        // GET LOGGED IN USER
        val sharedPref = getSharedPreferences("UserSession", MODE_PRIVATE)
        val userName = sharedPref.getString("name", "Unknown User") ?: "Unknown User"

        // SAVE ORDER TO DATABASE
        RetrofitClient.instance.checkout(
            userName,
            items,
            total
        ).enqueue(object : Callback<LoginResponse> {

            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {

                if (response.isSuccessful && response.body()?.success == true) {

                    Toast.makeText(
                        this@CheckoutActivity,
                        "Order Successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {

                    Toast.makeText(
                        this@CheckoutActivity,
                        "Checkout Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                Toast.makeText(
                    this@CheckoutActivity,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        // OK BUTTON
        binding.proceedBtn.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)

            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
            finish()
        }
    }
}