package com.vukkylimited.saladmobile

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebView.WebViewTransport
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat.startActivity


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
        saladBrowsing.settings.setSupportMultipleWindows(true)
        saladBrowsing.webChromeClient = SaladWebChromeClient()

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

private class SaladWebChromeClient : WebChromeClient() {
    override fun onCreateWindow(
        view: WebView, isDialog: Boolean,
        isUserGesture: Boolean, resultMsg: Message
    ): Boolean {
        val newWebView = WebView(view.context)
        view.addView(newWebView)
        val transport = resultMsg.obj as WebViewTransport
        transport.webView = newWebView
        resultMsg.sendToTarget()
        newWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                val browserIntent = Intent(Intent.ACTION_VIEW)
                browserIntent.data = Uri.parse(url)
                view.context.startActivity(browserIntent)
                return true
            }
        }
        return true
    }
}