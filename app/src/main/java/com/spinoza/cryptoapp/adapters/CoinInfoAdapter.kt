package com.spinoza.cryptoapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.spinoza.cryptoapp.R
import com.spinoza.cryptoapp.pojo.CoinPriceInfo
import com.spinoza.cryptoapp.utils.CoinPriceInfoListDiffUtilCallback
import com.squareup.picasso.Picasso


class CoinInfoAdapter(private val context: Context) :
    RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var onCoinClickListener: OnCoinClickListener? = null

    var coinPriceInfoList: List<CoinPriceInfo> = listOf()
        set(value) {
            val diffUtilCallback = CoinPriceInfoListDiffUtilCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_coin_info, parent, false)
        return CoinInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        with(holder) {
            with(coinPriceInfoList[position]) {
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
                itemView.setOnClickListener { onCoinClickListener?.onCoinClick(this) }
            }
        }
    }

    override fun getItemCount() = coinPriceInfoList.size

    inner class CoinInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewLogoCoin: ImageView = itemView.findViewById(R.id.imageViewLogoCoin)
        val textViewSymbols: TextView = itemView.findViewById(R.id.textViewSymbols)
        val textViewPrice: TextView = itemView.findViewById(R.id.textViewPrice)
        val textViewLastUpdate: TextView = itemView.findViewById(R.id.textViewLastUpdate)
    }

    interface OnCoinClickListener {
        fun onCoinClick(coinPriceInfo: CoinPriceInfo)
    }
}