package com.github.corentinc.notificoin.ui.home

import android.content.Context
import android.os.Bundle
import android.os.PowerManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.corentinc.core.home.HomeInteractor
import com.github.corentinc.core.search.Search
import com.github.corentinc.logger.analytics.NotifiCoinEvent.ButtonClicked
import com.github.corentinc.logger.analytics.NotifiCoinEvent.ScreenStarted
import com.github.corentinc.logger.analytics.NotifiCoinEventButtonName.ADD_SEARCH
import com.github.corentinc.logger.analytics.NotifiCoinEventParameter.ButtonName
import com.github.corentinc.logger.analytics.NotifiCoinEventParameter.Screen
import com.github.corentinc.logger.analytics.NotifiCoinEventScreen.HOME
import com.github.corentinc.notificoin.AnalyticsEventSender
import com.github.corentinc.notificoin.R
import com.github.corentinc.notificoin.ui.batteryWarning.PowerManagementPackages
import com.github.corentinc.notificoin.ui.home.searchesRecyclerView.SearchAdapter
import com.github.corentinc.notificoin.ui.home.searchesRecyclerView.SearchAdapterListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment(
    val homeInteractor: HomeInteractor,
    private val adapter: SearchAdapter
): Fragment(), HomeDisplay {
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var onDestinationChangedListener: NavController.OnDestinationChangedListener

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
        AnalyticsEventSender.sendEvent(
            ScreenStarted(Screen(HOME))
        )
        homeFragmentSearchesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
        }
        homeFragmentCreateAdButton.setOnClickListener {
            AnalyticsEventSender.sendEvent(
                ButtonClicked(
                    ButtonName(ADD_SEARCH),
                    Screen(HOME)
                )
            )
            homeInteractor.onCreateAdButtonPressed()
        }
        val context = requireContext()
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as? PowerManager
        bindViewModel()
        val homeFragmentArgs: HomeFragmentArgs by navArgs()
        homeInteractor.onStart(
            powerManager != null && powerManager.isIgnoringBatteryOptimizations(context.packageName),
            homeViewModel.shouldShowBatteryWhiteListAlertDialog.value == false,
            PowerManagementPackages.isAnyIntentCallable(context),
            homeFragmentArgs.id,
            homeFragmentArgs.title,
            homeFragmentArgs.url
        )
        addOnDestinationChangedListener()
        super.onStart()
    }

    private fun addOnDestinationChangedListener() {
        onDestinationChangedListener =
            NavController.OnDestinationChangedListener { _, destination, _ ->
                context?.let {
                    if (destination.label != resources.getString(R.string.titleHome)) {
                        beforeFragmentPause()
                    }
                }
            }
        findNavController().addOnDestinationChangedListener(onDestinationChangedListener)

    }

    override fun onPause() {
        beforeFragmentPause()
        findNavController().removeOnDestinationChangedListener(onDestinationChangedListener)
        super.onPause()
    }

    private fun beforeFragmentPause() {
        if (adapter.isSearchAdsPositionListInitialized()) {
            homeInteractor.beforeFragmentPause(adapter.searchAdsPositionList)
        }
    }

    override fun onDestroyView() {
        adapter.searchAdsPositionList = mutableListOf()
        super.onDestroyView()
    }

    override fun displayEditSearchScreen(id: Int?, url: String?, title: String?) {
        if (id != null && url != null && title != null) {
            findNavController()
                .navigate(
                    HomeFragmentDirections.homeToEditSearchAction(
                        id,
                        url,
                        title
                    )
                )
        } else {
            findNavController()
                .navigate(HomeFragmentDirections.homeToEditSearchAction())
        }
    }

    override fun displayAdListScreen(searchId: Int) {
        findNavController()
            .navigate(
                HomeFragmentDirections.homeToAdListAction(
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

    override fun displayBatteryWarningFragment(
        shouldDisplayDefaultDialog: Boolean
    ) {
        homeViewModel.shouldShowBatteryWhiteListAlertDialog.value = false
        findNavController().navigate(
            HomeFragmentDirections.homeToBatteryWarningAction(shouldDisplayDefaultDialog)
        )
    }

    override fun displayEmptySearches() {
        homeContentViewSwitcher.displayedChild = 1
        homeGlowingCircleView.startCircleAnimation()
    }

    private fun bindViewModel() {
        homeViewModel.searchAdsPositionList.observe(
            this.viewLifecycleOwner,
            Observer {
                homeContentViewSwitcher.displayedChild = 0
                homeGlowingCircleView.stopAnimation()
                createRecyclerView(it)
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
}