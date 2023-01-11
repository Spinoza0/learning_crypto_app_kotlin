package com.spinoza.cryptoapp.presentation.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.spinoza.cryptoapp.R

class CoinInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageViewLogoCoin: ImageView = itemView.findViewById(R.id.imageViewLogoCoin)
    val textViewSymbols: TextView = itemView.findViewById(R.id.textViewSymbols)
    val textViewPrice: TextView = itemView.findViewById(R.id.textViewPrice)
    val textViewLastUpdate: TextView = itemView.findViewById(R.id.textViewLastUpdate)
}