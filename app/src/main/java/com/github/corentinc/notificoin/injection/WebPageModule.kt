package com.github.corentinc.notificoin.injection

import com.github.corentinc.repository.webpage.DocumentToAdJsonArrayTransformer
import com.github.corentinc.repository.webpage.WebPageDataSource
import com.github.corentinc.repository.webpage.WebPageRepository
import org.koin.dsl.module

val webPageModule = module {
	single { WebPageDataSource() }
	single { WebPageRepository(get()) }
	single { DocumentToAdJsonArrayTransformer() }
}