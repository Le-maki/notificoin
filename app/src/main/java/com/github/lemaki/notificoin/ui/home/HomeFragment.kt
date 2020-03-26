package com.github.lemaki.notificoin.ui.home

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.github.lemaki.core.home.HomeInteractor
import com.github.lemaki.core.search.Search
import com.github.lemaki.notificoin.R
import com.github.lemaki.notificoin.ui.alarmManager.NotifiCoinAlarmManager
import com.github.lemaki.notificoin.ui.home.searchesRecyclerView.SearchAdapter
import com.github.lemaki.notificoin.ui.home.searchesRecyclerView.SearchAdapterListener
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment(
    val homeInteractor: HomeInteractor,
    private val alarmManager: NotifiCoinAlarmManager,
    private val adapter: SearchAdapter
): Fragment(), HomeDisplay {
    private val homeViewModel: HomeViewModel by sharedViewModel()

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
        super.onStart()
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
        homeViewModel.searchList.observe(
            this.viewLifecycleOwner,
            Observer {
                createRecyclerView(it)
            }
        )
    }

    private fun createRecyclerView(searchList: MutableList<Search>) {
        adapter.searchList = searchList
        adapter.searchAdapterListener = object: SearchAdapterListener {
            override fun onSearchDeleted(search: Search) {
                homeInteractor.onSearchDeleted(search)
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
}