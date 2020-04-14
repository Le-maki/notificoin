package com.github.corentinc.core.ad

import org.joda.time.DateTime

data class Ad(val title: String, val publicationDate: DateTime, val price: Int?, val url: String)