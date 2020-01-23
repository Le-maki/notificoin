package com.github.lemaki.notificoin.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.lemaki.notificoin.R
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment: Fragment() {

	private lateinit var dashboardViewModel: DashboardViewModel

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
		val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
		dashboardViewModel.text.observe(this, Observer {
			text_dashboard.text = it
		})
		return root
	}
}