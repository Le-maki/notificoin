package com.github.corentinc.notificoin.ui.editSearch

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.navigation.NavController.OnDestinationChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.corentinc.core.EditSearchInteractor
import com.github.corentinc.core.editSearch.UrlError.INVALID_FORMAT
import com.github.corentinc.core.editSearch.UrlError.NOT_A_SEARCH
import com.github.corentinc.notificoin.R
import com.github.corentinc.notificoin.ui.ChildFragment
import com.github.corentinc.notificoin.ui.hideKeyboard
import kotlinx.android.synthetic.main.fragment_edit_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class EditSearchFragment(private val editSearchInteractor: EditSearchInteractor): ChildFragment() {
    private val editSearchFragmentArgs: EditSearchFragmentArgs by navArgs()
    private lateinit var onDestinationChangedListener: OnDestinationChangedListener
    private val editSearchViewModel: EditSearchViewModel by viewModel()
    override fun onStart() {
        super.onStart()
        bindViewModel()
        addOnDestinationChangedListener()
        editSearchSaveButton.setOnClickListener {
            editSearchInteractor.onSave(
                editSearchFragmentArgs.id,
                editSearchTitleEditText.text.toString(),
                editSearchUrlEditText.text.toString()
            )
            findNavController().navigateUp()
        }
        editSearchDeleteButton.setOnClickListener {
            editSearchInteractor.deleteSearch(editSearchFragmentArgs.id)
            findNavController().navigate(
                EditSearchFragmentDirections.editSearchToHomeAction(
                    editSearchFragmentArgs.id,
                    editSearchTitleEditText.text.toString(),
                    editSearchUrlEditText.text.toString()
                )
            )
        }
        editSearchInteractor.onStart(
            editSearchFragmentArgs.id,
            editSearchFragmentArgs.title,
            editSearchFragmentArgs.url
        )
        initializeUrlEditText()
        initializeTitleEditText()
    }

    private fun initializeTitleEditText() {
        editSearchTitleEditText.doOnTextChanged { text, _, _, _ ->
            editSearchInteractor.onTitleTextChanged(
                text,
                editSearchUrlEditText.text.toString(),
                editSearchViewModel.urlError.value == null
            )
        }
    }

    private fun initializeUrlEditText() {
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
        editSearchUrlEditText.doOnTextChanged { text, _, _, _ ->
            editSearchInteractor.onUrlTextChanged(text, editSearchTitleEditText.text.toString())
        }
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
        findNavController().removeOnDestinationChangedListener(onDestinationChangedListener)
        super.onPause()
    }

    private fun bindViewModel() {
        editSearchViewModel.title.observe(
            this.viewLifecycleOwner,
            Observer {
                editSearchTitleEditText.text = Editable.Factory().newEditable(it)
            }
        )
        editSearchViewModel.url.observe(
            this.viewLifecycleOwner,
            Observer {
                editSearchUrlEditText.text = Editable.Factory().newEditable(it)
            }
        )
        editSearchViewModel.urlError.observe(
            this.viewLifecycleOwner,
            Observer {
                when (it) {
                    INVALID_FORMAT -> showUrlError(getString(R.string.edtiSearchInvalidUrlError))
                    NOT_A_SEARCH -> showUrlError(getString(R.string.editSearchNotASearchUrlError))
                    null -> hideUrlError()
                }
            }
        )
        editSearchViewModel.isSaveButtonEnabled.observe(
            this.viewLifecycleOwner,
            Observer {
                editSearchSaveButton.isEnabled = it
            }
        )
    }

    private fun showUrlError(text: String) {
        editSearchUrlErrorText.isVisible = true
        editSearchUrlErrorText.text = text
    }

    private fun hideUrlError() {
        editSearchUrlErrorText.isVisible = false
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