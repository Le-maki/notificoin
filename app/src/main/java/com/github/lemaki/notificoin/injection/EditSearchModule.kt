package com.github.lemaki.notificoin.injection

import com.github.lemaki.core.EditSearchInteractor
import org.koin.dsl.module

val editSearchModule = module {
    single { EditSearchInteractor(get()) }
}