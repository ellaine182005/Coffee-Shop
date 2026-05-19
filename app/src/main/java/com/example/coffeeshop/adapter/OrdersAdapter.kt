package com.example.coffeeshop.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeshop.databinding.ItemOrderBinding
import com.example.coffeeshop.model.OrderModel

class OrdersAdapter(
    private val orders: List<OrderModel>
) : RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

    class OrderViewHolder(val binding: ItemOrderBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {

        val binding = ItemOrderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {

        val order = orders[position]

        holder.binding.orderIdTxt.text = "Order ID: ${order.id}"
        holder.binding.customerTxt.text = order.user_name
        holder.binding.itemsTxt.text = order.items
        holder.binding.totalTxt.text = "₱${order.total_price}"
        holder.binding.statusTxt.text = order.status
    }

    override fun getItemCount(): Int = orders.size
}