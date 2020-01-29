package com.github.lemaki.notificoin.ui.home

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.room.Room
import com.github.lemaki.notificoin.R
import com.github.lemaki.notificoin.data.dataSources.AdDataSource
import com.github.lemaki.notificoin.data.dataSources.SearchDataSource
import com.github.lemaki.notificoin.data.dataSources.WebPageDataSource
import com.github.lemaki.notificoin.data.database.NotifiCoinDataBase
import com.github.lemaki.notificoin.data.repositories.AdRepository
import com.github.lemaki.notificoin.data.repositories.SearchRepository
import com.github.lemaki.notificoin.data.repositories.WebPageRepository
import com.github.lemaki.notificoin.data.transformers.DocumentToAdJsonArrayTransformer
import com.github.lemaki.notificoin.domain.home.HomeErrorType.CONNECTION
import com.github.lemaki.notificoin.domain.home.HomeErrorType.PARSING
import com.github.lemaki.notificoin.domain.home.HomeErrorType.UNKNOWN
import com.github.lemaki.notificoin.domain.home.HomeInteractor
import com.github.lemaki.notificoin.ui.ad.AdListToAdsListViewModelTransformer
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: Fragment() {

	private lateinit var homeInteractor: HomeInteractor
	private val homeViewModel: HomeViewModel by viewModels()
	lateinit var notifiCoinDataBase: NotifiCoinDataBase

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		notifiCoinDataBase = Room.databaseBuilder(context!!, NotifiCoinDataBase::class.java, "ad.db").build()
		homeInteractor = HomeInteractor(
			SearchRepository(SearchDataSource(notifiCoinDataBase.searchDao())),
			AdRepository(
				WebPageRepository(WebPageDataSource()),
				DocumentToAdJsonArrayTransformer(),
				AdDataSource(notifiCoinDataBase.adDao())
			),
			HomePresenter(AdListToAdsListViewModelTransformer(), homeViewModel)
		)
		bindViewModel()
		return inflater.inflate(R.layout.fragment_home, container, false)
	}

	private fun bindViewModel() {
		homeViewModel.adListViewModel.observe(this.viewLifecycleOwner, Observer {
			textHome?.text = it.text
			hideProgressBar()
		})
		homeViewModel.errorType.observe(this.viewLifecycleOwner, Observer { homeErrorType ->
			homeErrorType?.let {
				val text = when (it) {
					CONNECTION -> getString(R.string.homeConnectionErrorMessage)
					PARSING -> getString(R.string.homeParsingErrorMessage)
					UNKNOWN -> getString(R.string.homeUnknownErrorMessage)
				}
				textHome?.text = text
				hideProgressBar()
			}
		})
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		textHome?.movementMethod = ScrollingMovementMethod()
		homeInteractor.onStart()
		super.onViewCreated(view, savedInstanceState)
	}

	private fun hideProgressBar() {
		progressBarHome?.isVisible = false
	}

}