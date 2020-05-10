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
import com.github.corentinc.core.editSearch.UrlError.*
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
            findNavController().navigateUp()
        }
        editSearchDeleteButton.setOnClickListener {
            editSearchInteractor.deleteSearch(editSearchFragmentArgs.id)
            findNavController().navigateUp()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeUrlEditText()
        initializeTitleEditText()
        super.onViewCreated(view, savedInstanceState)
    }
    private fun initializeTitleEditText() {
        editSearchTitleEditText.text = Editable.Factory().newEditable(editSearchFragmentArgs.title)
        editSearchTitleEditText.doOnTextChanged { text, _, _, _ ->
            editSearchInteractor.onTitleTextChanged(text)
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
        editSearchUrlEditText.text = Editable.Factory().newEditable(editSearchFragmentArgs.url)
        editSearchUrlEditText.doOnTextChanged { text, _, _, _ ->
            editSearchInteractor.onUrlTextChanged(text)
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
        editSearchViewModel.isTitleEmpty.observe(
            this.viewLifecycleOwner,
            Observer {
                editSearchTitleErrorText.isVisible = it
                editSearchSaveButton.isEnabled =
                    editSearchViewModel.urlError.value == null && it == false
            }
        )
        editSearchViewModel.urlError.observe(
            this.viewLifecycleOwner,
            Observer {
                when (it) {
                    EMPTY -> showUrlError(getString(R.string.editSearchEmptyUrlError))
                    INVALID_FORMAT -> showUrlError(getString(R.string.edtiSearchInvalidUrlError))
                    NOT_A_SEARCH -> showUrlError(getString(R.string.editSearchNotASearchUrlError))
                    null -> hideUrlError()
                }
            }
        )
    }

    private fun showUrlError(text: String) {
        editSearchUrlErrorText.isVisible = true
        editSearchUrlErrorText.text = text
        editSearchSaveButton.isEnabled = false
    }

    private fun hideUrlError() {
        if (editSearchViewModel.isTitleEmpty.value != true) {
            editSearchSaveButton.isEnabled = true
        }
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