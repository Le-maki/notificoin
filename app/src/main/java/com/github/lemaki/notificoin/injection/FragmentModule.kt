package com.github.lemaki.notificoin.injection

import androidx.navigation.fragment.NavHostFragment
import com.github.lemaki.notificoin.ui.adList.AdListFragment
import com.github.lemaki.notificoin.ui.editSearch.EditSearchFragment
import com.github.lemaki.notificoin.ui.home.HomeFragment
import com.github.lemaki.notificoin.ui.notifications.NotificationsFragment
import org.koin.dsl.module

val fragmentModule = module {
    single { HomeFragment(get(), get(), get()) }
    single { NavHostFragment() }
    factory { EditSearchFragment(get()) }
    single { AdListFragment(get()) }
    single { NotificationsFragment() }
}