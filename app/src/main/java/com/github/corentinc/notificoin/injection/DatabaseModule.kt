package com.github.corentinc.notificoin.injection

import androidx.room.Room
import com.github.corentinc.repository.NotifiCoinDataBase
import org.koin.dsl.module

val databaseModule = module {
	single { Room.databaseBuilder(get(), NotifiCoinDataBase::class.java, "ad.db").build() }
}