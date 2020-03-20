package com.github.lemaki.notificoin.ui.editSearch

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController.OnDestinationChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.lemaki.core.EditSearchInteractor
import com.github.lemaki.notificoin.R
import kotlinx.android.synthetic.main.fragment_edit_search.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class EditSearchFragment: Fragment() {
    private val editSearchFragmentArgs: EditSearchFragmentArgs by navArgs()
    private val editSearchInteractor: EditSearchInteractor by inject()
    lateinit var onDestinationChangedListener: OnDestinationChangedListener

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
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        editSearchTitleEditText.text = Editable.Factory().newEditable(editSearchFragmentArgs.title)
        editSearchUrlEditText.text = Editable.Factory().newEditable(editSearchFragmentArgs.url)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onPause() {
        findNavController().removeOnDestinationChangedListener(onDestinationChangedListener)
        super.onPause()
    }

    private fun onNavigateUp() {
        CoroutineScope(Dispatchers.IO).launch {
            editSearchInteractor.onNavigateUp(
                editSearchFragmentArgs.id,
                editSearchTitleEditText.text.toString(),
                editSearchUrlEditText.text.toString()
            )
        }
    }
}