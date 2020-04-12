package com.github.corentinc.notificoin.ui

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

open class ChildFragment: Fragment() {
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onStart() {
        addOnBackPressedCallBack()
        super.onStart()
    }

    override fun onPause() {
        onBackPressedCallback.remove()
        super.onPause()
    }

    private fun addOnBackPressedCallBack() {
        onBackPressedCallback =
            object: OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigateUp()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }
}