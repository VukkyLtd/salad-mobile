package com.vukkylimited.saladmobile

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView


class MainActivity : Activity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Actual Salad Mobile code
        val saladBrowsing: WebView = findViewById(R.id.salad_view)
        saladBrowsing.loadUrl("https://app.salad.io")
        saladBrowsing.setInitialScale(1)
        saladBrowsing.settings.javaScriptEnabled = true
        saladBrowsing.settings.domStorageEnabled = true
        saladBrowsing.settings.useWideViewPort = true
        saladBrowsing.settings.loadWithOverviewMode = true

        // Device checks
        val display = windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)

        val density = resources.displayMetrics.density
        val dpWidth = outMetrics.widthPixels / density
        if (dpWidth < 768) {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.issues_title))
                .setMessage(getString(R.string.width_issues))
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_info)
                .show()
        } else if (dpWidth > 768) {
            saladBrowsing.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        }

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.portrait_title))
                .setMessage(getString(R.string.portrait_message))
                .setPositiveButton(android.R.string.yes) { _, _ ->
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                }
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }
    }
}