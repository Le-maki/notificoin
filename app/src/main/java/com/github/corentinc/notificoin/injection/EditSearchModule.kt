package com.github.corentinc.notificoin.injection

import com.github.corentinc.core.EditSearchInteractor
import com.github.corentinc.core.ui.editSearch.EditSearchPresenter
import com.github.corentinc.notificoin.ui.SingleLiveEvent
import com.github.corentinc.notificoin.ui.editSearch.EditSearchPresenterImpl
import com.github.corentinc.notificoin.ui.editSearch.EditSearchViewModel
import org.koin.dsl.module

val editSearchModule = module {
    single { EditSearchInteractor(searchRepository = get(), editSearchPresenter = get()) }
    single<EditSearchPresenter> { EditSearchPresenterImpl(editSearchViewModel = get()) }
    single {
        EditSearchViewModel(isTitleEmpty = SingleLiveEvent(), urlError = SingleLiveEvent())
    }
}