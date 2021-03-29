package com.dsvag.yandex.ui.stock

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dsvag.yandex.R
import com.dsvag.yandex.base.recyclerview.Adapter
import com.dsvag.yandex.base.recyclerview.ViewTyped
import com.dsvag.yandex.base.showToast
import com.dsvag.yandex.databinding.FragmentStockDetailsBinding
import com.dsvag.yandex.models.yandex.stock.response.StockResponse
import com.dsvag.yandex.ui.MainHolderFactory
import com.dsvag.yandex.ui.holders.NewsListUI
import com.dsvag.yandex.ui.holders.NewsUI
import com.dsvag.yandex.ui.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class StockDetailsFragment : Fragment(R.layout.fragment_stock_details) {

    private val binding by viewBinding(FragmentStockDetailsBinding::bind)

    private val stockViewModel by viewModels<StockViewModel>()

    private val newsAdapter by lazy(LazyThreadSafetyMode.NONE) { Adapter<ViewTyped>(MainHolderFactory()) }

    private val viewPagerAdapter by lazy(LazyThreadSafetyMode.NONE) {
        Adapter<ViewTyped>(MainHolderFactory()).apply {
            items = listOf(NewsListUI(newsAdapter))
        }
    }

    private val tabConfigurationStrategy = TabLayoutMediator.TabConfigurationStrategy { tab, position ->
        when (position) {
            0 -> tab.text = requireContext().getString(R.string.news)
            else -> error("Unknown position")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager, tabConfigurationStrategy).attach()

        binding.back.setOnClickListener { findNavController().popBackStack() }

        lifecycleScope.launchWhenCreated {
            stockViewModel.stateFlow.collect(::stateObserver)
        }
    }

    override fun onStart() {
        super.onStart()

        arguments?.getString("ticker")?.let { stockViewModel.fetchStock(it) }
            ?: run { findNavController().popBackStack() }
    }

    private fun stateObserver(state: StockViewModel.State) {
        when (state) {
            StockViewModel.State.Loading -> binding.shimmer.showShimmer(true)
            is StockViewModel.State.Success -> {
                binding.shimmer.hideShimmer()
                setData(state.stockResponse)
            }
            is StockViewModel.State.Error -> {
                binding.shimmer.hideShimmer()
                requireContext().showToast(state.msg)
                findNavController().popBackStack()
            }
        }
    }

    private fun setData(stockResponse: StockResponse) {
        binding.ticker.text = stockResponse.data.instruments.metaData.ticker
        binding.company.text = stockResponse.data.instruments.metaData.displayName

        newsAdapter.items = stockResponse.data.news.related.items.map { NewsUI(it) }
    }
}