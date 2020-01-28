package com.github.lemaki.notificoin.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.lemaki.notificoin.R
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationsFragment: Fragment() {

	private lateinit var notificationsViewModel: NotificationsViewModel

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
		val root = inflater.inflate(R.layout.fragment_notifications, container, false)
		notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
			text_notifications.text = it
		})
		return root
	}
}