package com.github.corentinc.notificoin.injection

import androidx.lifecycle.MutableLiveData
import com.github.corentinc.core.EditSearchInteractor
import com.github.corentinc.core.ui.editSearch.EditSearchPresenter
import com.github.corentinc.notificoin.ui.editSearch.EditSearchPresenterImpl
import com.github.corentinc.notificoin.ui.editSearch.EditSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val editSearchModule = module {
    single { EditSearchInteractor(searchRepository = get(), editSearchPresenter = get()) }
    single<EditSearchPresenter> { EditSearchPresenterImpl() }

    viewModel {
        EditSearchViewModel(
            savedTitle = MutableLiveData(),
            title = MutableLiveData(),
            url = MutableLiveData(),
            urlError = MutableLiveData(),
            isSaveButtonEnabled = MutableLiveData(),
            isUrlInfoTextVisible = MutableLiveData(),
            UrlButtonDisplayedChild = MutableLiveData()
        )
    }
}