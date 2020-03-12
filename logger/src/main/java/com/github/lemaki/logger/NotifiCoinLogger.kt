package com.github.lemaki.logger

import java.util.logging.Level
import java.util.logging.Logger

class NotifiCoinLogger {
	companion object {
		private const val TAG: String = "NOTIFICOIN "
        private val logger = Logger.getLogger(TAG)
		@JvmOverloads
		fun e(message: String, throwable: Throwable? = null) {
            logger.log(Level.SEVERE, message, throwable)
		}

		@JvmOverloads
		fun i(message: String, throwable: Throwable? = null) {
            logger.log(Level.INFO, message, throwable)
		}
	}
}