package com.example.coffeeshop.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coffeeshop.activity.DetailedActivity
import com.example.coffeeshop.databinding.ViewholderOfferBinding
import com.example.coffeeshop.model.ItemsModel

class OffersAdapter(private val items: List<ItemsModel>) :
    RecyclerView.Adapter<OffersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderOfferBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]

        holder.binding.titleTxt.text = item.title
        holder.binding.priceTxt.text = "₱${item.price}"

        Glide.with(holder.itemView.context)
            .load(item.picUrl[0])
            .into(holder.binding.shapeableImageView)

        // 🔥 CLICK FIX
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailedActivity::class.java)
            intent.putExtra("object", item)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(val binding: ViewholderOfferBinding) :
        RecyclerView.ViewHolder(binding.root)
}