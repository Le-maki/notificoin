package com.github.corentinc.notificoin.ui.adList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.corentinc.core.adList.AdListErrorType.*
import com.github.corentinc.core.adList.AdListInteractor
import com.github.corentinc.logger.analytics.NotifiCoinEvent.ScreenStarted
import com.github.corentinc.logger.analytics.NotifiCoinEventParameter.Screen
import com.github.corentinc.logger.analytics.NotifiCoinEventScreen.LIST_OF_ADS
import com.github.corentinc.notificoin.AnalyticsEventSender
import com.github.corentinc.notificoin.R
import com.github.corentinc.notificoin.createChromeIntentFromUrl
import com.github.corentinc.notificoin.ui.ChildFragment
import com.github.corentinc.notificoin.ui.adList.adListRecyclerView.AdListAdapter
import kotlinx.android.synthetic.main.fragment_ad_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AdListFragment(
    private val adListInteractor: AdListInteractor
): ChildFragment() {
    private val adListViewModel: AdListViewModel by viewModel()
    private val adapter = AdListAdapter()

    companion object {
        const val LEBONCOIN_URL = "http://www.leboncoin.fr"
    }

    override fun onStart() {
        AnalyticsEventSender.sendEvent(
            ScreenStarted(
                Screen(LIST_OF_ADS)
            )
        )
        super.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ad_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindViewModel()
        refresh()
        adListFragmentSwipeRefresh.setOnRefreshListener {
            refresh()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        adapter.adViewModelList = mutableListOf()
        super.onDestroyView()
    }

    private fun bindViewModel() {
        adListViewModel.adViewModelList.observe(
            this.viewLifecycleOwner,
            Observer {
                if (!adapter.isListInitialised() || (adapter.isListInitialised() && adapter.adViewModelList != it)) {
                    adapter.adViewModelList = it
                    adListFragmentRecyclerView.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = this@AdListFragment.adapter
                    }
                }
                hideProgressBar()
                hideErrorMessage()
                showAdsRecyclerView()
                stopRefreshing()
            })
        adListViewModel.errorType.observe(
            this.viewLifecycleOwner,
            Observer { adListErrorType ->
                textAdsFragment.setOnClickListener {}
                adListErrorType?.let {
                    val text = when (it) {
                        CONNECTION -> getString(R.string.adListConnectionErrorMessage)
                        PARSING -> getString(R.string.adListParsingErrorMessage)
                        UNKNOWN -> getString(R.string.adListUnknownErrorMessage)
                        FORBIDDEN -> {
                            textAdsFragment.setOnClickListener {
                                LEBONCOIN_URL.createChromeIntentFromUrl(requireActivity().packageManager)
                                    ?.let { intent ->
                                        startActivity(intent)
                                    } ?: Toast.makeText(
                                    requireContext(),
                                    getString(R.string.adListForbiddenFixMessage),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            getString(R.string.adListForbiddenErrorMessage)
                        }
                        EMPTY -> getString(R.string.adListEmpty)
                    }
                    textAdsFragment?.text = text
                    hideProgressBar()
                    hideAdsRecyclerView()
                    showErrorMessage()
                    stopRefreshing()
                }
            })
    }

    private fun hideProgressBar() {
        adListFragmentProgressBar?.isVisible = false
    }

    private fun stopRefreshing() {
        adListFragmentSwipeRefresh.isRefreshing = false
    }

    private fun showErrorMessage() {
        textAdsFragment?.isVisible = true
        adListImageView?.isVisible = true
    }

    private fun hideErrorMessage() {
        textAdsFragment?.isVisible = false
        adListImageView?.isVisible = false
    }

    private fun showAdsRecyclerView() {
        adListFragmentRecyclerView.isVisible = true
    }

    private fun hideAdsRecyclerView() {
        adListFragmentRecyclerView.isVisible = false
    }

    private fun refresh() {
        hideErrorMessage()
        val adListFragmentArgs: AdListFragmentArgs by navArgs()
        adListInteractor.onRefresh(adListFragmentArgs.searchId)
    }
}