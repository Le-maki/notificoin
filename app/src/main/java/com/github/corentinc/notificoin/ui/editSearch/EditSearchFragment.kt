package com.github.corentinc.notificoin.ui.editSearch

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController.OnDestinationChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.corentinc.core.EditSearchInteractor
import com.github.corentinc.notificoin.R
import com.github.corentinc.notificoin.ui.hideKeyboard
import kotlinx.android.synthetic.main.fragment_edit_search.*


class EditSearchFragment(private val editSearchInteractor: EditSearchInteractor): Fragment() {
    private val editSearchFragmentArgs: EditSearchFragmentArgs by navArgs()
    private lateinit var onDestinationChangedListener: OnDestinationChangedListener
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onStart() {
        super.onStart()
        addOnDestinationChangedListener()
        addOnBackPressedCallBack()
        editSearchUrlEditText.imeOptions = EditorInfo.IME_ACTION_DONE
        editSearchUrlEditText.setRawInputType(InputType.TYPE_CLASS_TEXT)
        editSearchUrlEditText.setOnEditorActionListener { _, editorAction, _ ->
            when (editorAction) {
                EditorInfo.IME_ACTION_DONE -> {
                    requireActivity().hideKeyboard()
                    true
                }
                else -> false
            }
        }
        editSearchTitleEditText.text = Editable.Factory().newEditable(editSearchFragmentArgs.title)
        editSearchUrlEditText.text = Editable.Factory().newEditable(editSearchFragmentArgs.url)
        editSearchSaveButton.setOnClickListener {
            findNavController().navigateUp()
        }
        editSearchDeleteButton.setOnClickListener {
            editSearchInteractor.deleteSearch(editSearchFragmentArgs.id)
            findNavController().navigateUp()
        }
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

    private fun addOnDestinationChangedListener() {
        onDestinationChangedListener =
            OnDestinationChangedListener { _, destination, _ ->
                if (destination.label == getString(R.string.titleHome)) {
                    onNavigateUp()
                }
            }
        findNavController().addOnDestinationChangedListener(onDestinationChangedListener)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_search, container, false)
    }

    override fun onPause() {
        onBackPressedCallback.remove()
        findNavController().removeOnDestinationChangedListener(onDestinationChangedListener)
        super.onPause()
    }

    private fun onNavigateUp() {
        requireActivity().hideKeyboard()
        editSearchInteractor.onNavigateUp(
            editSearchFragmentArgs.id,
            editSearchTitleEditText.text.toString(),
            editSearchUrlEditText.text.toString()
        )
    }
}