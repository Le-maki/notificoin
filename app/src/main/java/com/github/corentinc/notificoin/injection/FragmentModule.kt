package com.github.corentinc.notificoin.injection

import androidx.navigation.fragment.NavHostFragment
import com.github.corentinc.notificoin.MainActivity
import com.github.corentinc.notificoin.ui.adList.AdListFragment
import com.github.corentinc.notificoin.ui.batteryWarning.BatteryWarningFragment
import com.github.corentinc.notificoin.ui.editSearch.EditSearchFragment
import com.github.corentinc.notificoin.ui.home.HomeFragment
import com.github.corentinc.notificoin.ui.settings.AboutFragment
import com.github.corentinc.notificoin.ui.settings.SettingsFragment
import org.koin.androidx.fragment.dsl.fragment
import org.koin.dsl.module

val fragmentModule = module {
    scope<MainActivity> {
        fragment { HomeFragment(homeInteractor = get(), adapter = get()) }
        fragment { NavHostFragment() }
        fragment { EditSearchFragment(editSearchInteractor = get()) }
        fragment { AdListFragment(adListInteractor = get()) }
        fragment { SettingsFragment(settingsInteractor = get()) }
        fragment { AboutFragment() }
        fragment { BatteryWarningFragment(batteryWarningInteractor = get()) }
    }
}