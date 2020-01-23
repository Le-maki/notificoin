package com.github.lemaki.notificoin.ui.home

interface HomeDisplay {
	fun displayError(homeViewModel: HomeViewModel)
	fun displayAdList(homeViewModel: HomeViewModel)
}