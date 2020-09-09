package com.github.corentinc.notificoin.ui.batteryWarning

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import com.github.corentinc.core.ui.SpecialConstructor
import com.github.corentinc.core.ui.SpecialConstructor.*
import com.github.corentinc.logger.NotifiCoinLogger
import java.util.*


object PowerManagementPackages {
    fun isAnyIntentCallable(context: Context): Boolean {
        return POWER_MANAGER_INTENTS_MAP.any { entry ->
            entry.value.any { intent ->
                isIntentCallable(context, intent)
            }
        }
    }

    fun findCallableConstructor(context: Context): SpecialConstructor? {
        POWER_MANAGER_INTENTS_MAP.forEach { entry ->
            if (entry.value.any { intent ->
                    isIntentCallable(context, intent)
                }
            ) {
                return entry.key
            }
        }
        return null
    }

    fun findCallableIntent(context: Context): SpecialConstructorIntent? {
        POWER_MANAGER_INTENTS_MAP.forEach { entry ->
            entry.value.find { intent ->
                isIntentCallable(context, intent)
            }?.let { foundIntent ->
                return SpecialConstructorIntent(entry.key, foundIntent)
            }
        }
        return null
    }

    private fun isIntentCallable(context: Context, intent: Intent): Boolean {
        return try {
            val list =
                context.packageManager.queryIntentActivities(
                    intent,
                    PackageManager.MATCH_DEFAULT_ONLY
                )
            list.size > 0
        } catch (ignored: Exception) {
            false
        }
    }

    fun logDeviceBrandActivities(context: Context) {
        val brand = Build.BRAND.toLowerCase(Locale.getDefault())
        val manufacturer = Build.MANUFACTURER.toLowerCase(Locale.getDefault())
        val devicePackageInfoList =
            context.packageManager.getInstalledPackages(PackageManager.GET_ACTIVITIES)
        devicePackageInfoList.sortWith({ packageInfo1, packageInfo2 ->
            packageInfo1.packageName.compareTo(
                packageInfo2.packageName
            )
        })
        devicePackageInfoList.forEach { packageInfo ->
            val packageName = packageInfo.packageName.toLowerCase(Locale.getDefault())
            if (packageName.contains(brand) || packageName.contains(manufacturer)) {
                var activityFound = false
                val activityLog = StringBuilder()
                if (packageInfo.activities != null && packageInfo.activities.isNotEmpty()) {
                    val activityList = packageInfo.activities.asList().toMutableList()
                    activityList.sortWith({ activityInfo1, activityInfo2 ->
                        activityInfo1.name.compareTo(
                            activityInfo2.name
                        )
                    })
                    activityList.forEach { activityInfo ->
                        val activityName = activityInfo.name.toLowerCase(Locale.getDefault())
                        if (activityName.contains(brand) || activityName.contains(manufacturer)) {
                            activityLog.append("  Activity: ").append(activityInfo.name)
                            if (activityInfo.permission != null && activityInfo.permission.isNotEmpty()) {
                                activityLog.append(" - Permission: " + activityInfo.permission)
                            }
                            activityLog.append("\n")
                            activityFound = true
                        }
                    }
                }
                if (activityFound) {
                    NotifiCoinLogger.e("brand.activities PackageName: ${packageInfo.packageName}")
                    NotifiCoinLogger.e("brand.activities $activityLog")
                } else {
                    NotifiCoinLogger.e("brand.activities no activity found for package ${packageInfo.packageName}")
                }
            }
        }
    }
}

data class SpecialConstructorIntent(val constructor: SpecialConstructor, val intent: Intent)

private val POWER_MANAGER_INTENTS_MAP: Map<SpecialConstructor, List<Intent>> = mapOf(
    XIAOMI to
            listOf(
                Intent().setComponent(
                    ComponentName(
                        "com.miui.securitycenter",
                        "com.miui.permcenter.autostart.AutoStartManagementActivity"
                    )
                )
            ),
    LETV to
            listOf(
                Intent().setComponent(
                    ComponentName(
                        "com.letv.android.letvsafe",
                        "com.letv.android.letvsafe.AutobootManageActivity"
                    )
                )
            ),
    ASUS to
            listOf(
                Intent().setComponent(
                    ComponentName(
                        "com.asus.mobilemanager",
                        "com.asus.mobilemanager.autostart.AutoStartActivity"
                    )
                ),
                Intent().setComponent(
                    ComponentName(
                        "com.asus.mobilemanager",
                        "com.asus.mobilemanager.entry.FunctionActivity"
                    )
                ).setData(Uri.parse("mobilemanager://function/entry/AutoStart"))
            ),
    HUAWEI to
            listOf(
                Intent().setComponent(
                    ComponentName(
                        "com.huawei.systemmanager",
                        "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"
                    )
                ),
                Intent().setComponent(
                    ComponentName(
                        "com.huawei.systemmanager",
                        "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity"
                    )
                ),
                Intent().setComponent(
                    ComponentName(
                        "com.huawei.systemmanager",
                        "com.huawei.systemmanager.optimize.process.ProtectActivity"
                    )
                )
            ),
    OPPO to
            listOf(
                Intent().setComponent(
                    ComponentName(
                        "com.coloros.safecenter",
                        "com.coloros.safecenter.permission.startup.StartupAppListActivity"
                    )
                ),
                Intent().setComponent(
                    ComponentName(
                        "com.coloros.safecenter",
                        "com.coloros.safecenter.startupapp.StartupAppListActivity"
                    )
                ),
                Intent().setComponent(
                    ComponentName(
                        "com.oppo.safe",
                        "com.oppo.safe.permission.startup.StartupAppListActivity"
                    )
                )
            ),
    VIVO to
            listOf(
                Intent().setComponent(
                    ComponentName(
                        "com.iqoo.secure",
                        "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity"
                    )
                ),
                Intent().setComponent(
                    ComponentName(
                        "com.iqoo.secure",
                        "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager"
                    )
                ),
                Intent().setComponent(
                    ComponentName(
                        "com.vivo.permissionmanager",
                        "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"
                    )
                )
            ),
    NOKIA to
            listOf(
                Intent().setComponent(
                    ComponentName(
                        "com.evenwell.powersaving.g3",
                        "com.evenwell.powersaving.g3.exception.PowerSaverExceptionActivity"
                    )
                )
            ),
    SAMSUNG to
            listOf(
                Intent().setComponent(
                    ComponentName(
                        "com.samsung.android.lool",
                        "com.samsung.android.sm.ui.battery.BatteryActivity"
                    )
                )
            ),
    ONEPLUS to
            listOf(
                Intent().setComponent(
                    ComponentName(
                        "com.oneplus.security",
                        "com.oneplus.security.chainlaunch.view.ChainLaunchAppListActivity"
                    )
                )
            ),
    HTC to
            listOf(
                Intent().setComponent(
                    ComponentName(
                        "com.htc.pitroad",
                        "com.htc.pitroad.landingpage.activity.LandingPageActivity"
                    )
                )
            ),
    DEWAP to
            listOf(
                Intent().setComponent(
                    ComponentName(
                        "com.dewav.dwappmanager",
                        "com.dewav.dwappmanager.memory.SmartClearupWhiteList"
                    )
                )
            )
)
