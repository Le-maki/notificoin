package com.github.corentinc.notificoin.injection

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.github.corentinc.core.repository.GlobalSharedPreferencesRepository
import com.github.corentinc.core.repository.SharedPreferencesRepository
import com.github.corentinc.repository.sharedPreferences.GlobalSharedPreferencesRepositoryImpl
import com.github.corentinc.repository.sharedPreferences.SharedPreferencesRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val sharedPreferencesModule = module {
    single<SharedPreferencesRepository> {
        SharedPreferencesRepositoryImpl(
            sharedPreferencesEditor = get(),
            sharedPreferences = get()
        )
    }

    single {
        getSharedPrefs(context = get())
    }

    single<SharedPreferences.Editor> {
        getSharedPrefs(context = get()).edit()
    }

    single<GlobalSharedPreferencesRepository> {
        GlobalSharedPreferencesRepositoryImpl(
            sharedPreferencesEditor = get(named("global")),
            sharedPreferences = get(named("global"))
        )
    }

    single(named("global")) {
        getGlobalSharedPrefs(context = get())
    }

    single<SharedPreferences.Editor>(named("global")) {
        getGlobalSharedPrefs(context = get()).edit()
    }
}

fun getSharedPrefs(context: Context): SharedPreferences {
    return context.getSharedPreferences(
        SharedPreferencesRepository.PREFERENCE_FILE,
        Context.MODE_PRIVATE
    )
}

fun getGlobalSharedPrefs(context: Context): SharedPreferences {
    return PreferenceManager.getDefaultSharedPreferences(context)
}