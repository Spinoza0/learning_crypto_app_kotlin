package com.spinoza.cryptoapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.spinoza.cryptoapp.databinding.ActivityCoinDetailBinding
import com.squareup.picasso.Picasso

class CoinDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCoinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
        } else {
            viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
            intent.getStringExtra(EXTRA_FROM_SYMBOL)?.let {
                viewModel.getDetailInfo(it).observe(this) { coin ->
                    binding.textViewFromSymbol.text = coin.fromSymbol
                    binding.textViewToSymbol.text = coin.toSymbol
                    binding.textViewPrice.text = coin.price.toString()
                    binding.textViewMinPrice.text = coin.lowDay.toString()
                    binding.textViewMaxPrice.text = coin.highDay.toString()
                    binding.textViewLastMarket.text = coin.lastMarket
                    binding.textViewLastUpdate.text = coin.getFormattedTime()
                    Picasso.get().load(coin.getFullImageUrl()).into(binding.imageViewLogoCoin)
                }
            }
        }
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fromSymbol"

        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}