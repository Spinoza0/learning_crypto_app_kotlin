package com.spinoza.cryptoapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.spinoza.cryptoapp.data.repository.CoinRepositoryImpl
import com.spinoza.cryptoapp.databinding.ActivityCoinDetailBinding
import com.spinoza.cryptoapp.presentation.model.CoinViewModel
import com.spinoza.cryptoapp.presentation.model.CoinViewModelFactory
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
            viewModel = ViewModelProvider(
                this,
                CoinViewModelFactory(CoinRepositoryImpl(application))
            )[CoinViewModel::class.java]

            intent.getStringExtra(EXTRA_FROM_SYMBOL)?.let {
                viewModel.getDetailInfo(it).observe(this) { coin ->
                    binding.textViewFromSymbol.text = coin.fromSymbol
                    binding.textViewToSymbol.text = coin.toSymbol
                    binding.textViewPrice.text = coin.price
                    binding.textViewMinPrice.text = coin.lowDay
                    binding.textViewMaxPrice.text = coin.highDay
                    binding.textViewLastMarket.text = coin.lastMarket
                    binding.textViewLastUpdate.text = coin.lastUpdate
                    Picasso.get().load(coin.imageUrl).into(binding.imageViewLogoCoin)
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