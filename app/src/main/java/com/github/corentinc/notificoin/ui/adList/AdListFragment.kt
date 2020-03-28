package com.github.corentinc.notificoin.ui.adList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.corentinc.core.adList.AdListErrorType.*
import com.github.corentinc.core.adList.AdListInteractor
import com.github.corentinc.notificoin.R
import kotlinx.android.synthetic.main.fragment_ad_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AdListFragment(private val adListInteractor: AdListInteractor): Fragment() {

    private val adListViewModel: AdListViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ad_list, container, false)
    }

    override fun onStart() {
        bindViewModel()
        adListInteractor.onStart()
        super.onStart()
    }

    private fun bindViewModel() {
        adListViewModel.adListTextViewModel.observe(
            this.viewLifecycleOwner,
            Observer {
                textAdsFragment?.text = it.text
                hideProgressBar()
            })
        adListViewModel.errorType.observe(
            this.viewLifecycleOwner,
            Observer { adListErrorType ->
                adListErrorType?.let {
                    val text = when (it) {
                        CONNECTION -> getString(R.string.adListConnectionErrorMessage)
                        PARSING -> getString(R.string.adListParsingErrorMessage)
                        UNKNOWN -> getString(R.string.adListUnknownErrorMessage)
                    }
                    textAdsFragment?.text = text
                    hideProgressBar()
                }
            })
    }

    private fun hideProgressBar() {
        progressBarAdsFragment?.isVisible = false
    }
}