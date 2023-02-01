package com.spinoza.cryptoapp.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.spinoza.cryptoapp.data.model.CoinPriceInfo

class CoinPriceInfoDiffCallback(
) : DiffUtil.ItemCallback<CoinPriceInfo>() {
    override fun areItemsTheSame(oldItem: CoinPriceInfo, newItem: CoinPriceInfo): Boolean {
        return oldItem.fromSymbol == newItem.fromSymbol
    }

    override fun areContentsTheSame(oldItem: CoinPriceInfo, newItem: CoinPriceInfo): Boolean {
        return oldItem.toSymbol == newItem.toSymbol &&
                oldItem.price == newItem.price &&
                oldItem.lastUpdate == newItem.lastUpdate
    }
}