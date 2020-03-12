package com.github.lemaki.notificoin.injection

import com.github.lemaki.repository.webpage.DocumentToAdJsonArrayTransformer
import com.github.lemaki.repository.webpage.WebPageDataSource
import com.github.lemaki.repository.webpage.WebPageRepository
import org.koin.dsl.module

val webPageModule = module {
	single { WebPageDataSource() }
	single { WebPageRepository(get()) }
	single { DocumentToAdJsonArrayTransformer() }
}