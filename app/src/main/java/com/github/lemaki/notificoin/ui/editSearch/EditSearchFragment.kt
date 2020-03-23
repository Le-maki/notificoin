package com.github.lemaki.notificoin.ui.editSearch

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.NavController.OnDestinationChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.lemaki.core.EditSearchInteractor
import com.github.lemaki.notificoin.R
import com.github.lemaki.notificoin.ui.hideKeyboard
import kotlinx.android.synthetic.main.fragment_edit_search.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class EditSearchFragment(private val editSearchInteractor: EditSearchInteractor): Fragment() {
    private val editSearchFragmentArgs: EditSearchFragmentArgs by navArgs()
    private lateinit var onDestinationChangedListener: OnDestinationChangedListener

    override fun onStart() {
        super.onStart()
        onDestinationChangedListener =
            OnDestinationChangedListener { _, destination, _ ->
                if (destination.label == getString(R.string.titleHome)) {
                    onNavigateUp()
                    findNavController().removeOnDestinationChangedListener(
                        onDestinationChangedListener
                    )
                }
            }
        findNavController().addOnDestinationChangedListener(onDestinationChangedListener)
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
            requireActivity().onBackPressed()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_search, container, false)
    }

    override fun onPause() {
        findNavController().removeOnDestinationChangedListener(onDestinationChangedListener)
        super.onPause()
    }

    private fun onNavigateUp() {
        CoroutineScope(Dispatchers.IO).launch {
            requireActivity().hideKeyboard()
            editSearchInteractor.onNavigateUp(
                editSearchFragmentArgs.id,
                editSearchTitleEditText.text.toString(),
                editSearchUrlEditText.text.toString()
            )
        }
    }
}