package com.spinoza.cryptoapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.spinoza.cryptoapp.pojo.CoinPriceInfo

class CoinPriceInfoListDiffUtilCallback(
    private var oldList: List<CoinPriceInfo>,
    private var newList: List<CoinPriceInfo>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].fromSymbol == newList[newItemPosition].fromSymbol
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.toSymbol == newItem.toSymbol &&
                oldItem.price == newItem.price &&
                oldItem.lastUpdate == newItem.lastUpdate
    }
}