package com.spinoza.cryptoapp.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.spinoza.cryptoapp.data.network.ApiFactory
import com.spinoza.cryptoapp.data.database.AppDataBase
import com.spinoza.cryptoapp.databinding.ActivityCoinPriceListBinding
import com.spinoza.cryptoapp.presentation.adapter.CoinInfoAdapter
import com.spinoza.cryptoapp.presentation.model.CoinViewModel
import com.spinoza.cryptoapp.presentation.model.CoinViewModelFactory

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

        viewModel = ViewModelProvider(this,
            CoinViewModelFactory(
                AppDataBase.getInstance(application).coinPriceInfoDao(),
                ApiFactory.apiService
            )
        )[CoinViewModel::class.java]

        viewModel.priceList.observe(this) { adapter.submitList(it) }
        viewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }
}