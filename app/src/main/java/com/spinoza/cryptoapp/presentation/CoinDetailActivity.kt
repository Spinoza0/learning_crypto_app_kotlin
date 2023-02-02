package com.spinoza.cryptoapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.spinoza.cryptoapp.R
import com.spinoza.cryptoapp.databinding.ActivityCoinDetailBinding
import com.spinoza.cryptoapp.presentation.fragment.CoinDetailFragment

class CoinDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCoinDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
        } else {
            intent.getStringExtra(EXTRA_FROM_SYMBOL)?.let {
                if (savedInstanceState == null) {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, CoinDetailFragment.newInstance(it))
                        .commit()
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