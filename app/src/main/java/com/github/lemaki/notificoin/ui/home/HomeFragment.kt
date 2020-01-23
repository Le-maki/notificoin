package com.github.lemaki.notificoin.ui.home

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.github.lemaki.notificoin.R
import com.github.lemaki.notificoin.data.repositories.AdRepository
import com.github.lemaki.notificoin.data.transformers.DocumentToAdJsonArrayTransformer
import com.github.lemaki.notificoin.data.dataSources.WebPageDataSource
import com.github.lemaki.notificoin.data.repositories.WebPageRepository
import com.github.lemaki.notificoin.domain.home.HomeErrorType.*
import com.github.lemaki.notificoin.domain.home.HomeInteractor
import com.github.lemaki.notificoin.ui.ad.AdListToAdsListViewModelTransformer
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), HomeDisplay {

	private lateinit var homeViewModel: HomeViewModel
	private val homeInteractor = HomeInteractor(
		AdRepository(
			WebPageRepository(
				WebPageDataSource()
			),
			DocumentToAdJsonArrayTransformer()
		), HomePresenter(this, AdListToAdsListViewModelTransformer())
	)
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
		return inflater.inflate(R.layout.fragment_home, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		textHome.movementMethod = ScrollingMovementMethod()
		homeInteractor.onStart()
		super.onViewCreated(view, savedInstanceState)
	}

	override fun displayError(homeViewModel: HomeViewModel) {
		homeViewModel.errorType?.let {
			val text = when(it) {
				CONNECTION -> getString(R.string.homeConnectionErrorMessage)
				PARSING -> getString(R.string.homeParsingErrorMessage)
				UNKNOWN -> getString(R.string.homeUnknownErrorMessage)
			}
			textHome?.text = text
			hideProgressBar()
		}
	}

	override fun displayAdList(homeViewModel: HomeViewModel) {
		textHome?.text = homeViewModel.adListViewModel?.text
		hideProgressBar()
	}

	private fun hideProgressBar() {
		progressBarHome?.isVisible = false
	}

}