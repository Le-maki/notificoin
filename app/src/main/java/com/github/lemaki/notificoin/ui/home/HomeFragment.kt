package com.github.lemaki.notificoin.ui.home

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.lemaki.notificoin.R
import com.github.lemaki.notificoin.domain.home.HomeErrorType.CONNECTION
import com.github.lemaki.notificoin.domain.home.HomeErrorType.PARSING
import com.github.lemaki.notificoin.domain.home.HomeErrorType.UNKNOWN
import com.github.lemaki.notificoin.domain.home.HomeInteractor
import com.github.lemaki.notificoin.ui.notificationPush.NotificationManager
import kotlinx.android.synthetic.main.fragment_home.notificationButton
import kotlinx.android.synthetic.main.fragment_home.progressBarHome
import kotlinx.android.synthetic.main.fragment_home.textHome
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class HomeFragment: Fragment() {
	private val homeInteractor: HomeInteractor by inject()
	private val homeViewModel: HomeViewModel by inject()
	private val notificationManager: NotificationManager by inject { parametersOf(this.context) }

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
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
		notificationButton?.setOnClickListener { notificationManager.sendNotification("HELLO THERE", "General Kenobi") }
		homeInteractor.onStart()
		super.onViewCreated(view, savedInstanceState)
	}

	private fun hideProgressBar() {
		progressBarHome?.isVisible = false
	}

}