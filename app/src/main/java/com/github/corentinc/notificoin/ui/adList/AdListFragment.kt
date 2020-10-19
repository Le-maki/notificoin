package com.github.corentinc.notificoin.ui.adList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.corentinc.core.adList.AdListErrorType
import com.github.corentinc.core.adList.AdListErrorType.*
import com.github.corentinc.core.adList.AdListInteractor
import com.github.corentinc.logger.analytics.NotifiCoinEvent
import com.github.corentinc.logger.analytics.NotifiCoinEvent.ScreenStarted
import com.github.corentinc.logger.analytics.NotifiCoinEventException
import com.github.corentinc.logger.analytics.NotifiCoinEventParameter
import com.github.corentinc.logger.analytics.NotifiCoinEventParameter.Screen
import com.github.corentinc.logger.analytics.NotifiCoinEventScreen.LIST_OF_ADS
import com.github.corentinc.notificoin.AnalyticsEventSender
import com.github.corentinc.notificoin.R
import com.github.corentinc.notificoin.createChromeIntentFromUrl
import com.github.corentinc.notificoin.ui.adList.adListRecyclerView.AdListAdapter
import kotlinx.android.synthetic.main.fragment_ad_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AdListFragment(
    private val adListInteractor: AdListInteractor
) : Fragment(), AdListDisplay {
    private val adListViewModel: AdListViewModel by sharedViewModel()
    private val adapter = AdListAdapter()

    companion object {
        const val LEBONCOIN_URL = "http://www.leboncoin.fr"
    }

    init {
        (adListInteractor.adListPresenter as AdListPresenterImpl).adListDisplay = this
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
        adapter.adViewDataList = mutableListOf()
        adListInteractor.stopRefresh()
        super.onDestroyView()
    }

    private fun bindViewModel() {
        adListViewModel.adViewDataList.observe(
            this.viewLifecycleOwner,
            {
                if (!adapter.isListInitialised() || (adapter.isListInitialised() && adapter.adViewDataList != it)) {
                    adapter.adViewDataList = it
                    adListFragmentRecyclerView.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = this@AdListFragment.adapter
                    }
                }
            })
        adListViewModel.errorType.observe(
            this.viewLifecycleOwner,
            { adListErrorType ->
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
                }
            })
    }

    override fun hideProgressBar() {
        adListFragmentProgressBar?.isVisible = false
    }

    override fun stopRefreshing() {
        adListFragmentSwipeRefresh.isRefreshing = false
    }

    override fun displayErrorMessage(isVisible: Boolean) {
        textAdsFragment?.isVisible = isVisible
        adListImageView?.isVisible = isVisible
    }

    override fun displayAdsRecyclerView(isVisible: Boolean) {
        adListFragmentRecyclerView?.isVisible = isVisible
    }

    private fun refresh() {
        displayErrorMessage(false)
        val adListFragmentArgs: AdListFragmentArgs by navArgs()
        adListInteractor.onRefresh(adListFragmentArgs.searchId)
    }

    private fun displayErrorAndSendEvent(
        errorType: AdListErrorType,
        eventType: NotifiCoinEventException
    ) {
        AnalyticsEventSender.sendEvent(
            NotifiCoinEvent.ExceptionThrown(
                NotifiCoinEventParameter.EventException(eventType),
                Screen(LIST_OF_ADS)
            )
        )
        adListViewModel.errorType.value = errorType
    }

    override fun displayConnectionError() {
        displayErrorAndSendEvent(CONNECTION, NotifiCoinEventException.CONNECTION)
    }

    override fun displayParsingError() {
        displayErrorAndSendEvent(PARSING, NotifiCoinEventException.PARSING)
    }

    override fun displayUnknownError() {
        displayErrorAndSendEvent(UNKNOWN, NotifiCoinEventException.UNKNOWN)
    }

    override fun displayAdList(adViewDataList: MutableList<AdViewData>) {
        adListViewModel.adViewDataList.value = adViewDataList
    }

    override fun displayForbiddenError() {
        displayErrorAndSendEvent(FORBIDDEN, NotifiCoinEventException.FORBIDDEN)
    }

    override fun displayEmptyList() {
        adListViewModel.errorType.value = EMPTY
    }
}