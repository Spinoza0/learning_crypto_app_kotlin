package com.spinoza.cryptoapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.spinoza.cryptoapp.adapters.CoinInfoAdapter
import com.spinoza.cryptoapp.databinding.ActivityCoinPriceListBinding

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCoinPriceListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = {
            val intent = CoinDetailActivity.newIntent(
                this@CoinPriceListActivity,
                it.fromSymbol
            )
            startActivity(intent)
        }

        binding.recyclerViewCoinPriceList.adapter = adapter

        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.priceList.observe(this) {
            adapter.coinPriceInfoList = it
        }
        viewModel.isError().observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }
}