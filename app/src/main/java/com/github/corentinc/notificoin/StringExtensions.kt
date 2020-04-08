package com.github.corentinc.notificoin

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri

fun String.createIntentFromUrl(): Intent {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(this)
    intent.apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    return intent
}

fun String.createChromeIntentFromUrl(packageManager: PackageManager): Intent? {
    val intent = this.createIntentFromUrl().setPackage("com.android.chrome")
    intent.resolveActivity(packageManager)?.let {
        return intent
    } ?: return null
}