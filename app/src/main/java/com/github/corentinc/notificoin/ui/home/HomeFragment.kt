package com.github.corentinc.notificoin.ui.home

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.github.corentinc.core.home.HomeInteractor
import com.github.corentinc.core.search.Search
import com.github.corentinc.notificoin.R
import com.github.corentinc.notificoin.ui.alarmManager.NotifiCoinAlarmManager
import com.github.corentinc.notificoin.ui.home.searchesRecyclerView.SearchAdapter
import com.github.corentinc.notificoin.ui.home.searchesRecyclerView.SearchAdapterListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment(
    val homeInteractor: HomeInteractor,
    private val alarmManager: NotifiCoinAlarmManager,
    private val adapter: SearchAdapter
): Fragment(), HomeDisplay {
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    init {
        (homeInteractor.homePresenter as HomePresenterImpl).homeDisplay = this
    }

    override fun onStart() {
        homeFragmentSearchesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
        }
        homeFragmentCreateAdButton.setOnClickListener {
            homeInteractor.onCreateAdButtonPressed()
        }
        val context = requireContext()
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as? PowerManager
        bindViewModel()
        homeInteractor.onStart(
            powerManager != null && powerManager.isIgnoringBatteryOptimizations(context.packageName),
            homeViewModel.shouldShowBatteryWhiteListAlertDialog.value == false
        )
        alarmManager.setAlarmManager()
        findNavController().addOnDestinationChangedListener { _, _, _ ->
            onFragmentChange()
        }
        super.onStart()
    }

    private fun onFragmentChange() {
        if (adapter.isSearchAdsPositionListInitialized()) {
            homeInteractor.onFragmentChange(adapter.searchAdsPositionList)
        }
    }

    override fun onDestroyView() {
        adapter.searchAdsPositionList = mutableListOf()
        super.onDestroyView()
    }

    override fun displayEditAdScreen(id: Int, url: String, title: String) {
        Navigation.findNavController(requireView())
            .navigate(
                HomeFragmentDirections.editSearchAction(
                    id,
                    url,
                    title
                )
            )
    }

    override fun displayAdListScreen(searchId: Int) {
        Navigation.findNavController(requireView())
            .navigate(
                HomeFragmentDirections.goToAdListAction(
                    searchId
                )
            )
    }

    override fun displayUndoDeleteSearch(search: Search) {
        Snackbar.make(
            homeFragmentSnackBar,
            getString(R.string.homeFragmentSearchDeleted),
            Snackbar.LENGTH_LONG
        )
            .setAction(getString(R.string.undo)) {
                adapter.searchAdsPositionList.add(search)
                adapter.notifyItemInserted(adapter.searchAdsPositionList.size)
                homeInteractor.onRestoreSearch(search)
            }.setActionTextColor(resources.getColor(R.color.primaryColor, requireContext().theme))
            .show()
    }

    private fun bindViewModel() {
        homeViewModel.shouldShowBatteryWhiteListAlertDialog.observe(
            this.viewLifecycleOwner,
            Observer {
                if (it) {
                    presentBatteryWhitelistRequestAlertDialog()
                    homeViewModel.shouldShowBatteryWhiteListAlertDialog.value = false
                }
            })
        homeViewModel.searchAdsPositionList.observe(
            this.viewLifecycleOwner,
            Observer {
                if (!adapter.isSearchAdsPositionListInitialized() || (adapter.isSearchAdsPositionListInitialized() && adapter.searchAdsPositionList != it)) {
                    createRecyclerView(it)
                }
            }
        )
    }

    private fun createRecyclerView(searchList: MutableList<Search>) {
        adapter.searchAdsPositionList = searchList
        adapter.searchAdapterListener = object: SearchAdapterListener {
            override fun onSearchDeleted(search: Search) {
                homeInteractor.onSearchDeleted(search)
            }

            override fun onSearchClicked(search: Search) {
                homeInteractor.onSearchClicked(search)
            }
        }
        homeFragmentSearchesRecyclerView.adapter = adapter
        adapter.touchHelper.attachToRecyclerView(homeFragmentSearchesRecyclerView)
    }

    private fun presentBatteryWhitelistRequestAlertDialog() {
        val context = requireContext()
        val alertMessage = getString(R.string.batteryWhiteListExplanation)
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val view: View = View.inflate(context, R.layout.battery_whitelist_alertdialog, null)
        Glide.with(context)
            .load(R.raw.battery_whitelist)
            .into(view.findViewById(R.id.batteryWhiteListGif))
        builder.setView(view)
        builder.setMessage(alertMessage)
        builder.setPositiveButton(getString(R.string.OK)) { _, _ ->
            try {
                goToBatteryWhiteListOfTheApp(context)
            } catch (exception: ActivityNotFoundException) {
                goToBatteryWhiteList()
            }
        }
        builder.setNeutralButton(getString(R.string.alertDialogStopAsking)) { _, _ ->
            homeInteractor.onBatteryWhiteListAlertDialogNeutralButtonPressed()
        }
        builder.setNegativeButton(getString(R.string.alertDialogMaybeLater)) { _, _ ->
        }
        builder.create().show()
    }

    private fun goToBatteryWhiteListOfTheApp(context: Context) {
        val intent = Intent()
        intent.action = Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS
        val packageName: String = context.packageName
        intent.data = Uri.parse("package:$packageName")
        startActivity(intent)
    }

    private fun goToBatteryWhiteList() {
        val intent = Intent()
        intent.action = Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS
        startActivity(intent)
    }
}