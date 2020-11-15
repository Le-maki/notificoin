package com.github.corentinc.notificoin.ui.editSearch

import android.content.ClipboardManager
import android.content.Context
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
import androidx.fragment.app.Fragment
import androidx.navigation.NavController.OnDestinationChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.corentinc.core.EditSearchInteractor
import com.github.corentinc.core.editSearch.UrlError
import com.github.corentinc.core.editSearch.UrlError.INVALID_FORMAT
import com.github.corentinc.core.editSearch.UrlError.NOT_A_SEARCH
import com.github.corentinc.core.ui.editSearch.EditSearchDisplay
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
import com.github.corentinc.notificoin.ui.adList.AdListFragment.Companion.LEBONCOIN_URL
import com.github.corentinc.notificoin.ui.hideKeyboard
import kotlinx.android.synthetic.main.fragment_edit_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditSearchFragment(private val editSearchInteractor: EditSearchInteractor) : Fragment(),
    EditSearchDisplay {
    private val editSearchFragmentArgs: EditSearchFragmentArgs by navArgs()
    private lateinit var onDestinationChangedListener: OnDestinationChangedListener
    private val editSearchViewModel: EditSearchViewModel by viewModel()
    private var clipboardManager: ClipboardManager? = null

    init {
        (editSearchInteractor.editSearchPresenter as EditSearchPresenterImpl).editSearchDisplay =
            this
    }

    override fun onStart() {
        AnalyticsEventSender.sendEvent(
            ScreenStarted(
                Screen(EDIT_SEARCH)
            )
        )
        super.onStart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            editSearchInteractor.onDeleteButtonClicked(editSearchFragmentArgs.id)

        }
        clipboardManager = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        editSearchUrlPasteButton.setOnClickListener {
            editSearchInteractor.onEditSearchUrlPasteButtonClicked(
                clipboardManager?.primaryClip?.getItemAt(
                    0
                )?.coerceToText(context).toString()
            )
        }
        editSearchInteractor.onStart(
            editSearchFragmentArgs.id,
            editSearchFragmentArgs.title,
            editSearchFragmentArgs.url,
            clipboardManager?.primaryClip?.getItemAt(0)?.coerceToText(context).toString(),
            editSearchViewModel.isViewModelIntialized.value,
            editSearchViewModel.title.value,
            editSearchViewModel.url.value
        )
        editSearchViewModel.isViewModelIntialized.value = true
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
        editSearchViewModel.title.value = editSearchTitleEditText.text.toString()
        editSearchViewModel.url.value = editSearchUrlEditText.text.toString()
        super.onPause()
    }

    private fun bindViewModel() {
        editSearchViewModel.title.observe(
            this.viewLifecycleOwner,
            {
                editSearchTitleEditText.text = Editable.Factory().newEditable(it)
            }
        )
        editSearchViewModel.url.observe(
            this.viewLifecycleOwner,
            {
                editSearchUrlEditText.text = Editable.Factory().newEditable(it)
            }
        )
        editSearchViewModel.urlError.observe(
            this.viewLifecycleOwner,
            {
                when (it) {
                    INVALID_FORMAT -> showUrlError(getString(R.string.edtiSearchInvalidUrlError))
                    NOT_A_SEARCH -> showUrlError(getString(R.string.editSearchNotASearchUrlError))
                    null -> hideUrlError()
                }
            }
        )
        editSearchViewModel.isSaveButtonEnabled.observe(
            this.viewLifecycleOwner,
            {
                editSearchSaveButton.isEnabled = it
            }
        )
        editSearchViewModel.isUrlInfoTextVisible.observe(
            this.viewLifecycleOwner,
            {
                if (it) {
                    editSearchViewModel.urlError.value = null
                }
                editSearchUrlInfoText?.isVisible = it
            }
        )
        editSearchViewModel.UrlButtonDisplayedChild.observe(
            this.viewLifecycleOwner,
            {
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

    override fun displayUrlError(error: UrlError) {
        editSearchViewModel.urlError.value = error
    }

    override fun displayValidUrl() {
        editSearchViewModel.urlError.value = null
    }

    override fun displayEditSearch(title: String, url: String) {
        editSearchViewModel.title.value = title
        editSearchViewModel.url.value = url
    }

    override fun displaySaveButton(isEnabled: Boolean) {
        editSearchViewModel.isSaveButtonEnabled.value = isEnabled
    }

    override fun displayUrlInfo(isVisible: Boolean) {
        editSearchViewModel.isUrlInfoTextVisible.value = isVisible
    }

    override fun displayCopiedContent(clipBoardText: String) {
        editSearchViewModel.url.value = clipBoardText
    }

    override fun displayUrlButtonDisplayedChild(displayedChildIndex: Int) {
        editSearchViewModel.UrlButtonDisplayedChild.value = displayedChildIndex
    }

    override fun displayNavigateUp() {
        findNavController().navigateUp()
    }

    override fun displayNavigateHomeAfterDeletion() {
        findNavController().navigate(
            EditSearchFragmentDirections.editSearchToHomeAction(
                editSearchFragmentArgs.id,
                editSearchTitleEditText.text.toString(),
                editSearchUrlEditText.text.toString()
            )
        )
    }
}