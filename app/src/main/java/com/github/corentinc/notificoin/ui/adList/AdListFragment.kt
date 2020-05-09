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
import com.github.corentinc.notificoin.R
import com.github.corentinc.notificoin.createChromeIntentFromUrl
import com.github.corentinc.notificoin.ui.ChildFragment
import com.github.corentinc.notificoin.ui.adList.adListRecyclerView.AdListAdapter
import kotlinx.android.synthetic.main.fragment_ad_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AdListFragment(
    private val adListInteractor: AdListInteractor
): ChildFragment() {
    companion object {
        const val LEBONCOIN_URL = "http://www.leboncoin.fr"
    }

    private val adListViewModel: AdListViewModel by viewModel()
    private val adapter = AdListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ad_list, container, false)
    }

    override fun onStart() {
        textAdsFragment?.isVisible = false
        adListImageView?.isVisible = false
        bindViewModel()
        val adListFragmentArgs: AdListFragmentArgs by navArgs()
        adListInteractor.onStart(adListFragmentArgs.searchId)
        super.onStart()
    }

    override fun onDestroyView() {
        adapter.adViewModelList = mutableListOf()
        super.onDestroyView()
    }

    private fun bindViewModel() {
        adListViewModel.adViewModelList.observe(
            this.viewLifecycleOwner,
            Observer {
                hideProgressBar()
                if (!adapter.isListInitialised() || (adapter.isListInitialised() && adapter.adViewModelList != it)) {
                    adapter.adViewModelList = it
                    adListFragmentRecyclerView.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = this@AdListFragment.adapter
                    }
                }
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
                    textAdsFragment?.isVisible = true
                    adListImageView?.isVisible = true
                    hideProgressBar()
                }
            })
    }

    private fun hideProgressBar() {
        adListFragmentProgressBar?.isVisible = false
    }
}