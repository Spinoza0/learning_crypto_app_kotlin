package com.spinoza.cryptoapp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.spinoza.cryptoapp.R
import com.spinoza.cryptoapp.data.model.CoinPriceInfo
import com.squareup.picasso.Picasso


class CoinInfoAdapter(private val context: Context) :
    ListAdapter<CoinPriceInfo, CoinInfoViewHolder>(CoinPriceInfoDiffCallback()) {

    var onCoinClickListener: ((CoinPriceInfo) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_coin_info, parent, false)
        return CoinInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        with(holder) {
            with(getItem(position)) {
                textViewSymbols.text = String.format(
                    context.getString(R.string.symbols_template),
                    fromSymbol,
                    toSymbol
                )
                textViewPrice.text = price.toString()
                textViewLastUpdate.text = String.format(
                    context.getString(R.string.last_update_template),
                    getFormattedTime()
                )
                Picasso.get().load(getFullImageUrl()).into(imageViewLogoCoin)
                itemView.setOnClickListener { onCoinClickListener?.invoke(this) }
            }
        }
    }
}