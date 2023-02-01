package com.spinoza.cryptoapp.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.spinoza.cryptoapp.data.network.model.CoinInfoDto

class CoinPriceInfoDiffCallback(
) : DiffUtil.ItemCallback<CoinInfoDto>() {
    override fun areItemsTheSame(oldItem: CoinInfoDto, newItem: CoinInfoDto): Boolean {
        return oldItem.fromSymbol == newItem.fromSymbol
    }

    override fun areContentsTheSame(oldItem: CoinInfoDto, newItem: CoinInfoDto): Boolean {
        return oldItem.toSymbol == newItem.toSymbol &&
                oldItem.price == newItem.price &&
                oldItem.lastUpdate == newItem.lastUpdate
    }
}