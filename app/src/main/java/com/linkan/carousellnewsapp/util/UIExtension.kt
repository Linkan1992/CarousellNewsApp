package com.linkan.carousellnewsapp.util

import android.app.Activity
import android.os.Build
import android.view.WindowInsetsController
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

fun AppCompatActivity.setStatusBarColorCompat(
    colorResId: Int, lightIcons: Boolean = false) {
    val window = window

    WindowCompat. setDecorFitsSystemWindows (window, true)

    val color = ContextCompat.getColor(this, colorResId)
    window.statusBarColor = color


    val insetsController = WindowInsetsControllerCompat(window, window.decorView)
    insetsController.isAppearanceLightStatusBars = lightIcons

    // Optional: also set navigation bar to match (if desired)
    window.navigationBarColor = color
    insetsController.isAppearanceLightNavigationBars = lightIcons
}


fun Activity.setSystemBarsColor(
    @ColorRes colorResId: Int,
    darkIcons: Boolean = false
) {
    val window = window
    val color = ContextCompat.getColor(this, colorResId)

    // Required: allow app to draw system bar backgrounds
    WindowCompat.setDecorFitsSystemWindows(window, true)

    // Official non-deprecated method (works for all SDKs)
    window.setFlags(
        android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,
        android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
    )

    // Set bar colors
    window.statusBarColor = color
    window.navigationBarColor = color

    // Control icon / text contrast using modern API
    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
            val controller = window.insetsController
            if (controller != null) {
                if (darkIcons) {
                    controller.setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    )
                    controller.setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                    )
                } else {
                    controller.setSystemBarsAppearance(
                        0,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    )
                    controller.setSystemBarsAppearance(
                        0,
                        WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                    )
                }
            }
        }
        else -> {
            // Backward compatible method for API < 30
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = if (darkIcons) {
                android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or
                        android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            } else {
                0
            }
        }
    }
}