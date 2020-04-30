package com.github.corentinc.notificoin.injection

import com.github.corentinc.core.EditSearchInteractor
import org.koin.dsl.module

val editSearchModule = module {
    single { EditSearchInteractor(searchRepository = get()) }
}