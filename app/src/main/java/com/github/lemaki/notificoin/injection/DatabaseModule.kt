package com.github.lemaki.notificoin.injection

import androidx.room.Room
import com.github.lemaki.notificoin.data.NotifiCoinDataBase
import org.koin.dsl.module

val databaseModule = module {
	single { Room.databaseBuilder<NotifiCoinDataBase>(get(), NotifiCoinDataBase::class.java, "ad.db").build() }
}