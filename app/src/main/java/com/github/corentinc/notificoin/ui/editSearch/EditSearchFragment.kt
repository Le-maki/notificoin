package com.github.corentinc.notificoin.ui.editSearch

import android.content.ClipboardManager
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.navigation.NavController.OnDestinationChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.corentinc.core.EditSearchInteractor
import com.github.corentinc.core.editSearch.UrlError.INVALID_FORMAT
import com.github.corentinc.core.editSearch.UrlError.NOT_A_SEARCH
import com.github.corentinc.logger.analytics.NotifiCoinEvent.ScreenStarted
import com.github.corentinc.logger.analytics.NotifiCoinEvent.SearchChanged
import com.github.corentinc.logger.analytics.NotifiCoinEventParameter.Screen
import com.github.corentinc.logger.analytics.NotifiCoinEventParameter.SearchStatus
import com.github.corentinc.logger.analytics.NotifiCoinEventScreen.EDIT_SEARCH
import com.github.corentinc.logger.analytics.NotifiCoinEventSearchStatus.SEARCH_DELETED
import com.github.corentinc.logger.analytics.NotifiCoinEventSearchStatus.SEARCH_SAVED
import com.github.corentinc.notificoin.AnalyticsEventSender
import com.github.corentinc.notificoin.R
import com.github.corentinc.notificoin.createChromeIntentFromUrl
import com.github.corentinc.notificoin.ui.ChildFragment
import com.github.corentinc.notificoin.ui.adList.AdListFragment.Companion.LEBONCOIN_URL
import com.github.corentinc.notificoin.ui.hideKeyboard
import kotlinx.android.synthetic.main.fragment_edit_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditSearchFragment(private val editSearchInteractor: EditSearchInteractor): ChildFragment() {
    private val editSearchFragmentArgs: EditSearchFragmentArgs by navArgs()
    private lateinit var onDestinationChangedListener: OnDestinationChangedListener
    private val editSearchViewModel: EditSearchViewModel by viewModel()
    private var clipboardManager: ClipboardManager? = null

    companion object {
        private var oldOrientationBit = 0
    }

    override fun onStart() {
        AnalyticsEventSender.sendEvent(
            ScreenStarted(
                Screen(EDIT_SEARCH)
            )
        )
        super.onStart()
        bindViewModel()
        addOnDestinationChangedListener()
        editSearchSaveButton.setOnClickListener {
            AnalyticsEventSender.sendEvent(
                SearchChanged(
                    SearchStatus(SEARCH_SAVED)
                )
            )
            editSearchInteractor.onSave(
                editSearchFragmentArgs.id,
                editSearchTitleEditText.text.toString(),
                editSearchUrlEditText.text.toString()
            )
            findNavController().navigateUp()
        }
        editSearchUrlInfoButton.setOnClickListener {
            editSearchInteractor.onUrlInfoButtonClicked()
        }
        editSearchUrlInfoText.setOnClickListener {
            startChromeIntent()
        }
        editSearchDeleteButton.setOnClickListener {
            AnalyticsEventSender.sendEvent(
                SearchChanged(
                    SearchStatus(SEARCH_DELETED)
                )
            )
            editSearchInteractor.deleteSearch(editSearchFragmentArgs.id)
            findNavController().navigate(
                EditSearchFragmentDirections.editSearchToHomeAction(
                    editSearchFragmentArgs.id,
                    editSearchTitleEditText.text.toString(),
                    editSearchUrlEditText.text.toString()
                )
            )
        }
        clipboardManager = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        editSearchUrlPasteButton.setOnClickListener {
            editSearchInteractor.onEditSearchUrlPasteButtonClicked(
                clipboardManager?.primaryClip?.getItemAt(
                    0
                )?.coerceToText(context).toString()
            )
        }
        if (oldOrientationBit and ActivityInfo.CONFIG_ORIENTATION != ActivityInfo.CONFIG_ORIENTATION) {
            editSearchInteractor.onStart(
                editSearchFragmentArgs.id,
                editSearchFragmentArgs.title,
                editSearchFragmentArgs.url,
                clipboardManager?.primaryClip?.getItemAt(0)?.coerceToText(context).toString()
            )
        } else {
            editSearchInteractor.onTextChanged(
                editSearchUrlEditText.text,
                editSearchTitleEditText.text.toString()
            )
        }
        initializeUrlEditText()
        initializeTitleEditText()

    }

    private fun startChromeIntent() {
        LEBONCOIN_URL.createChromeIntentFromUrl(requireActivity().packageManager)
            ?.let { intent ->
                editSearchViewModel.isUrlInfoTextVisible.value = false
                startActivity(intent)
            } ?: Toast.makeText(
            requireContext(),
            getString(R.string.adListForbiddenFixMessage),
            Toast.LENGTH_LONG
        ).show()
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

    override fun onDestroy() {
        super.onDestroy()
        oldOrientationBit = activity?.changingConfigurations ?: 0
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
        editSearchViewModel.isUrlInfoTextVisible.observe(
            this.viewLifecycleOwner,
            Observer {
                if (it) {
                    editSearchViewModel.urlError.value = null
                }
                editSearchUrlInfoText?.isVisible = it
            }
        )
        editSearchViewModel.UrlButtonDisplayedChild.observe(
            this.viewLifecycleOwner,
            Observer {
                editSearchUrlButton.displayedChild = it
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
            editSearchViewModel.isUrlInfoTextVisible.value = false
            editSearchInteractor.onTextChanged(text, editSearchTitleEditText.text.toString())
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
}