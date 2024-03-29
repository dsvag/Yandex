package com.dsvag.yandex.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dsvag.yandex.R
import com.dsvag.yandex.base.ErrorType
import com.dsvag.yandex.base.recyclerview.Adapter
import com.dsvag.yandex.base.recyclerview.ViewTyped
import com.dsvag.yandex.base.showToast
import com.dsvag.yandex.databinding.FragmentStockListBinding
import com.dsvag.yandex.models.Stock
import com.dsvag.yandex.ui.MainHolderFactory
import com.dsvag.yandex.ui.holders.StockListUI
import com.dsvag.yandex.ui.holders.StockUI
import com.dsvag.yandex.ui.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class StockListFragment : Fragment(R.layout.fragment_stock_list) {

    private val binding by viewBinding(FragmentStockListBinding::bind)

    private val stocksViewModel by viewModels<StocksListViewModel>()

    private val defaultStockAdapter by lazy(LazyThreadSafetyMode.NONE) {
        Adapter<ViewTyped>(MainHolderFactory(onStockClick = ::changeFavoriteStatus))
    }

    private val favoriteStockAdapter by lazy(LazyThreadSafetyMode.NONE) {
        Adapter<ViewTyped>(MainHolderFactory(onStockClick = ::changeFavoriteStatus))
    }

    private val viewPagerAdapter by lazy(LazyThreadSafetyMode.NONE) {
        Adapter<ViewTyped>(MainHolderFactory()).apply {
            add(StockListUI(defaultStockAdapter, uId = "Default"))
            add(StockListUI(favoriteStockAdapter, uId = "Favorite"))
        }
    }

    private val tabConfigurationStrategy = TabConfigurationStrategy { tab, position ->
        when (position) {
            0 -> tab.text = requireContext().getString(R.string.stocks)
            1 -> tab.text = requireContext().getString(R.string.favorite)
            else -> error("Unknown position")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager, tabConfigurationStrategy).attach()

        binding.search.setOnClickListener {
            findNavController().navigate(R.id.action_stockListFragment_to_searchFragment)
        }

        lifecycleScope.launchWhenStarted {
            stocksViewModel.defaultStockFlow.collect { stockList ->
                defaultStockAdapter.items = stockList.map { StockUI(it) }
            }
        }

        lifecycleScope.launchWhenStarted {
            stocksViewModel.favoriteStockFlow.collect { stockList ->
                favoriteStockAdapter.items = stockList.map { StockUI(it) }
            }
        }

        lifecycleScope.launchWhenCreated {
            stocksViewModel.stateFlow.collect(::stateObserver)
        }
    }

    private fun changeFavoriteStatus(stock: Stock) {
        stocksViewModel.changeFavoriteStatus(stock)
    }

    private fun stateObserver(state: StocksListViewModel.State) {
        when (state) {
            StocksListViewModel.State.Default -> stocksViewModel.subscribe()
            is StocksListViewModel.State.Error -> throwError(state.errorType)
        }
    }

    private fun throwError(errorType: ErrorType) {
        val msg = when (errorType) {
            ErrorType.Network -> requireContext().getString(R.string.error_network)
            ErrorType.Server -> requireContext().getString(R.string.error_server)
            else -> requireContext().getString(R.string.error_unknown)
        }
        requireContext().showToast(msg)
        findNavController().popBackStack()
    }
}