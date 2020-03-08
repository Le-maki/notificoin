package com.github.lemaki.notificoin.injection

import android.content.Context
import android.content.SharedPreferences
import com.github.lemaki.notificoin.data.sharedPreferences.SharedPreferencesRepository
import org.koin.dsl.module

val sharedPreferencesModule = module {
    single { SharedPreferencesRepository(get(), get()) }
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