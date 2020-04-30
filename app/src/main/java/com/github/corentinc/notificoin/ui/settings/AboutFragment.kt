package com.github.corentinc.notificoin.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.corentinc.notificoin.R
import com.github.corentinc.notificoin.ui.ChildFragment
import mehdi.sakout.aboutpage.AboutPage

class AboutFragment: ChildFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return AboutPage(this.context)
            .isRTL(false)
            .setImage(R.drawable.ic_duck)
            .addGroup("Connect with us")
            .addEmail("elmehdi.sakout@gmail.com")
            .addPlayStore("com.ideashower.readitlater.pro")
            .addGitHub("medyo")
            .create()
    }
}