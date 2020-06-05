package com.github.corentinc.notificoin.injection

import androidx.navigation.fragment.NavHostFragment
import com.github.corentinc.notificoin.ui.adList.AdListFragment
import com.github.corentinc.notificoin.ui.batteryWarning.BatteryWarningFragment
import com.github.corentinc.notificoin.ui.editSearch.EditSearchFragment
import com.github.corentinc.notificoin.ui.home.HomeFragment
import com.github.corentinc.notificoin.ui.settings.AboutFragment
import com.github.corentinc.notificoin.ui.settings.SettingsFragment
import org.koin.dsl.module

val fragmentModule = module {
    single { HomeFragment(homeInteractor = get(), adapter = get()) }
    single { NavHostFragment() }
    factory { EditSearchFragment(editSearchInteractor = get()) }
    single { AdListFragment(adListInteractor = get()) }
    single { SettingsFragment(settingsInteractor = get()) }
    single { AboutFragment() }
    factory { BatteryWarningFragment(batteryWarningInteractor = get()) }
}