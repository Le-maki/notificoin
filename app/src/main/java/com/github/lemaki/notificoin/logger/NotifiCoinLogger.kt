package com.github.lemaki.notificoin.logger

import android.util.Log

class NotifiCoinLogger {
	companion object {
		private const val TAG: String = "NOTIFICOIN "
		@JvmOverloads
		fun e(message: String, throwable: Throwable? = null) {
			Log.e(TAG, message, throwable)
		}

		@JvmOverloads
		fun i(message: String, throwable: Throwable? = null) {
			Log.i(TAG, message, throwable)
		}
	}
}