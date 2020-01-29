package com.github.lemaki.notificoin.injection

import com.github.lemaki.notificoin.data.webpage.DocumentToAdJsonArrayTransformer
import com.github.lemaki.notificoin.data.webpage.WebPageDataSource
import com.github.lemaki.notificoin.data.webpage.WebPageRepository
import org.koin.dsl.module

val webPageModule = module {
	single { WebPageDataSource() }
	single { WebPageRepository(get()) }
	single { DocumentToAdJsonArrayTransformer() }
}