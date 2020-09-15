package com.vukkylimited.saladmobile

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
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

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.portrait_title))
                .setMessage(getString(R.string.portrait_message))
                .setPositiveButton(android.R.string.yes) { _, _ ->
                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                }
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }
    }
}