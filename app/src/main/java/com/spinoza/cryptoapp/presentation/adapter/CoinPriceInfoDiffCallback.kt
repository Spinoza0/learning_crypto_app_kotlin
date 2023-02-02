package com.spinoza.cryptoapp.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.spinoza.cryptoapp.domain.CoinInfo

class CoinPriceInfoDiffCallback() : DiffUtil.ItemCallback<CoinInfo>() {
    override fun areItemsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
        return oldItem.fromSymbol == newItem.fromSymbol
    }

    override fun areContentsTheSame(oldItem: CoinInfo, newItem: CoinInfo): Boolean {
        return oldItem.toSymbol == newItem.toSymbol &&
                oldItem.price == newItem.price &&
                oldItem.lastUpdate == newItem.lastUpdate
    }
}