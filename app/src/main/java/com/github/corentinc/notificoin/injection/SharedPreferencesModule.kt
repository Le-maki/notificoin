package com.github.corentinc.notificoin.injection

import android.content.Context
import android.content.SharedPreferences
import com.github.corentinc.core.repository.SharedPreferencesRepository
import com.github.corentinc.repository.sharedPreferences.SharedPreferencesRepositoryImpl
import org.koin.dsl.module

val sharedPreferencesModule = module {
    single<SharedPreferencesRepository> { SharedPreferencesRepositoryImpl(get(), get()) }
    single {
        getSharedPrefs(get())
    }

    single<SharedPreferences.Editor> {
        getSharedPrefs(get()).edit()
    }
}

fun getSharedPrefs(context: Context): SharedPreferences {
    return context.getSharedPreferences(
        SharedPreferencesRepository.PREFERENCE_FILE,
        Context.MODE_PRIVATE
    )
}