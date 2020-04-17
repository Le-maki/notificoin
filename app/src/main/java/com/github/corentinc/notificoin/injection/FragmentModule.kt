package com.github.corentinc.notificoin.injection

import androidx.navigation.fragment.NavHostFragment
import com.github.corentinc.notificoin.ui.adList.AdListFragment
import com.github.corentinc.notificoin.ui.editSearch.EditSearchFragment
import com.github.corentinc.notificoin.ui.home.HomeFragment
import com.github.corentinc.notificoin.ui.notifications.SettingsFragment
import org.koin.dsl.module

val fragmentModule = module {
    single { HomeFragment(get(), get(), get()) }
    single { NavHostFragment() }
    factory { EditSearchFragment(get()) }
    single { AdListFragment(get()) }
    single { SettingsFragment() }
}