package com.spinoza.cryptoapp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.spinoza.cryptoapp.R
import com.spinoza.cryptoapp.databinding.ItemCoinInfoBinding
import com.spinoza.cryptoapp.domain.CoinInfo
import com.squareup.picasso.Picasso


class CoinInfoAdapter(private val context: Context) :
    ListAdapter<CoinInfo, CoinInfoViewHolder>(CoinPriceInfoDiffCallback()) {

    var onCoinClickListener: ((CoinInfo) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding = ItemCoinInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CoinInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        with(holder.binding) {
            with(getItem(position)) {
                textViewSymbols.text = String.format(
                    context.getString(R.string.symbols_template),
                    fromSymbol,
                    toSymbol
                )
                textViewPrice.text = price.toString()
                textViewLastUpdate.text = String.format(
                    context.getString(R.string.last_update_template),
                    lastUpdate
                )
                Picasso.get().load(imageUrl).into(imageViewLogoCoin)
                root.setOnClickListener { onCoinClickListener?.invoke(this) }
            }
        }
    }
}