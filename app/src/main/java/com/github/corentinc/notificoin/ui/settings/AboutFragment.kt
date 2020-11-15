package com.github.corentinc.notificoin.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.corentinc.notificoin.R
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element

class AboutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val koinIntent = Intent(Intent.ACTION_VIEW)
        with(koinIntent) {
            data = Uri.parse("https://insert-koin.io/")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val flipperIntent = Intent(Intent.ACTION_VIEW)
        with(flipperIntent) {
            data = Uri.parse("https://github.com/facebook/flipper")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val glideIntent = Intent(Intent.ACTION_VIEW)
        with(glideIntent) {
            data = Uri.parse("https://github.com/bumptech/glide")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val aboutIntent = Intent(Intent.ACTION_VIEW)
        with(aboutIntent) {
            data = Uri.parse("https://github.com/medyo/android-about-page")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val bubbleViewIntent = Intent(Intent.ACTION_VIEW)
        with(bubbleViewIntent) {
            data = Uri.parse("https://github.com/lguipeng/BubbleView")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        return AboutPage(this.context)
            .isRTL(false)
            .setDescription(getString(R.string.aboutAppDescription))
            .setImage(R.drawable.ic_duck)
            .addGroup(getString(R.string.aboutConnectWithUsGroup))
            .addEmail(getString(R.string.aboutEmail), getString(R.string.aboutEmailTitle))
            .addPlayStore(getString(R.string.aboutPackageName))
            .addGitHub(getString(R.string.aboutGithubId), getString(R.string.aboutGithubTitle))
            .addGroup(getString(R.string.aboutDevelopedWith))
            .addItem(
                Element(getString(R.string.aboutKoin), R.drawable.ic_public_24px).setIntent(
                    koinIntent
                )
            )
            .addItem(
                Element(getString(R.string.aboutFlipper), R.drawable.ic_public_24px).setIntent(
                    flipperIntent
                )
            )
            .addItem(
                Element(getString(R.string.aboutGlide), R.drawable.ic_public_24px).setIntent(
                    glideIntent
                )
            )
            .addItem(
                Element(
                    getString(R.string.aboutAndroidAboutPage),
                    R.drawable.ic_public_24px
                ).setIntent(aboutIntent)
            )
            .addItem(
                Element(
                    getString(R.string.aboutBubbleView),
                    R.drawable.ic_public_24px
                ).setIntent(bubbleViewIntent)
            )
            .create()
    }
}