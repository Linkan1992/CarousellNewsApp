package com.linkan.carousellnewsapp.util

import android.os.Build
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

fun AppCompatActivity.setStatusBarColorAndIcons(
    colorResId: Int,
    darkIcons: Boolean
) {
    val color = ContextCompat.getColor(this, colorResId)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
        // Apply background color and padding for status bar using WindowInsets listener
        window.decorView.setOnApplyWindowInsetsListener { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.setBackgroundColor(color)
            view.setPadding(0, statusBarInsets.top, 0, 0)
            insets
        }

        // Control status bar icon colors (light or dark)
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = darkIcons
    } else {
        // For older versions (API 21-34), set status bar color directly
        window.statusBarColor = color
        val flags = window.decorView.systemUiVisibility
        window.decorView.systemUiVisibility = if (darkIcons) {
            flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
    }
}
