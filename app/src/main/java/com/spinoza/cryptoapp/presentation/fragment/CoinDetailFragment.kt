package com.spinoza.cryptoapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.spinoza.cryptoapp.data.repository.CoinRepositoryImpl
import com.spinoza.cryptoapp.databinding.FragmentCoinDetailBinding
import com.spinoza.cryptoapp.presentation.model.CoinViewModel
import com.spinoza.cryptoapp.presentation.model.CoinViewModelFactory
import com.squareup.picasso.Picasso

class CoinDetailFragment : Fragment() {

    private var _binding: FragmentCoinDetailBinding? = null
    private val binding: FragmentCoinDetailBinding
        get() = _binding ?: throw RuntimeException("FragmentCoinDetailBinding is null")

    private lateinit var viewModel: CoinViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCoinDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fromSymbol = getSymbol()

        viewModel = ViewModelProvider(
            this,
            CoinViewModelFactory(CoinRepositoryImpl(requireActivity().application))
        )[CoinViewModel::class.java]

        viewModel.getDetailInfo(fromSymbol).observe(viewLifecycleOwner) { coin ->
            with(binding) {
                textViewFromSymbol.text = coin.fromSymbol
                textViewToSymbol.text = coin.toSymbol
                textViewPrice.text = coin.price
                textViewMinPrice.text = coin.lowDay
                textViewMaxPrice.text = coin.highDay
                textViewLastMarket.text = coin.lastMarket
                textViewLastUpdate.text = coin.lastUpdate
                Picasso.get().load(coin.imageUrl).into(imageViewLogoCoin)
            }
        }
    }

    private fun getSymbol() = requireArguments().getString(EXTRA_FROM_SYMBOL, EMPTY_STRING)

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fromSymbol"
        private const val EMPTY_STRING = ""

        fun newInstance(fromSymbol: String): Fragment {
            return CoinDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_FROM_SYMBOL, fromSymbol)
                }
            }
        }
    }
}