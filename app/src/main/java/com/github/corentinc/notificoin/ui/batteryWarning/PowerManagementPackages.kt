package com.github.corentinc.notificoin.ui.batteryWarning

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri

object PowerManagementPackages {
    fun isAnyIntentCallable(context: Context): Boolean {
        return POWER_MANAGER_INTENTS.any {
            isIntentCallable(context, it)
        }
    }

    fun findCallableIntent(context: Context): Intent? {
        return POWER_MANAGER_INTENTS.find {
            isIntentCallable(context, it)
        }
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

    private val POWER_MANAGER_INTENTS: List<Intent> = listOf(
        // XIAOMI
        Intent().setComponent(
            ComponentName(
                "com.miui.securitycenter",
                "com.miui.permcenter.autostart.AutoStartManagementActivity"
            )
        ),
        // LETV
        Intent().setComponent(
            ComponentName(
                "com.letv.android.letvsafe",
                "com.letv.android.letvsafe.AutobootManageActivity"
            )
        ),
        // ASUS
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
        ).setData(Uri.parse("mobilemanager://function/entry/AutoStart")),
        // HUAWEI
        Intent().setComponent(
            ComponentName(
                "com.huawei.systemmanager",
                "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity"
            )
        ),
        Intent().setComponent(
            ComponentName(
                "com.huawei.systemmanager",
                "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"
            )
        ),
        Intent().setComponent(
            ComponentName(
                "com.huawei.systemmanager",
                "com.huawei.systemmanager.optimize.process.ProtectActivity"
            )
        ),
        // OPPO
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
        ),
        // VIVO
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
        ),
        // NOKIA
        Intent().setComponent(
            ComponentName(
                "com.evenwell.powersaving.g3",
                "com.evenwell.powersaving.g3.exception.PowerSaverExceptionActivity"
            )
        ),
        // SAMSUNG
        Intent().setComponent(
            ComponentName(
                "com.samsung.android.lool",
                "com.samsung.android.sm.ui.battery.BatteryActivity"
            )
        ),
        // ONEPLUS
        Intent().setComponent(
            ComponentName(
                "om.oneplus.security",
                "com.oneplus.security.chainlaunch.view.ChainLaunchAppListActivity"
            )
        ),
        // HTC
        Intent().setComponent(
            ComponentName(
                "com.htc.pitroad",
                "com.htc.pitroad.landingpage.activity.LandingPageActivity"
            )
        ),
        // DEWAP
        Intent().setComponent(
            ComponentName(
                "com.dewav.dwappmanager",
                "com.dewav.dwappmanager.memory.SmartClearupWhiteList"
            )
        )
    )
}