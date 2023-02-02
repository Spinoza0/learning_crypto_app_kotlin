package com.spinoza.cryptoapp.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.spinoza.cryptoapp.R
import com.spinoza.cryptoapp.data.repository.CoinRepositoryImpl
import com.spinoza.cryptoapp.databinding.ActivityCoinPriceListBinding
import com.spinoza.cryptoapp.presentation.adapter.CoinInfoAdapter
import com.spinoza.cryptoapp.presentation.fragment.CoinDetailFragment
import com.spinoza.cryptoapp.presentation.model.CoinViewModel
import com.spinoza.cryptoapp.presentation.model.CoinViewModelFactory

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel

    private val binding by lazy {
        ActivityCoinPriceListBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = {
            if (isOnePanelMode()) {
                launchDetailActivity(it.fromSymbol)
            } else {
                launchDetailFragment(it.fromSymbol)
            }
        }

        binding.recyclerViewCoinPriceList.adapter = adapter
        binding.recyclerViewCoinPriceList.itemAnimator = null

        viewModel = ViewModelProvider(
            this,
            CoinViewModelFactory(CoinRepositoryImpl(application))
        )[CoinViewModel::class.java]

        viewModel.coinInfoList.observe(this) { adapter.submitList(it) }
        viewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }

    private fun isOnePanelMode() = binding.fragmentContainer == null
    private fun launchDetailActivity(fromSymbol: String) {
        startActivity(CoinDetailActivity.newIntent(this@CoinPriceListActivity, fromSymbol))
    }

    private fun launchDetailFragment(fromSymbol: String) {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, CoinDetailFragment.newInstance(fromSymbol))
            .addToBackStack(null)
            .commit()
    }
}