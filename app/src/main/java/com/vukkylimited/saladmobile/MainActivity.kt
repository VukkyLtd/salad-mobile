package com.vukkylimited.saladmobile

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebView.WebViewTransport
import android.webkit.WebViewClient


class MainActivity : Activity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Actual Salad Mobile code
        val saladBrowsing: WebView = findViewById(R.id.salad_view)
        saladBrowsing.loadUrl("https://app.salad.io/earn/summary")
        saladBrowsing.setInitialScale(1)
        saladBrowsing.settings.javaScriptEnabled = true
        saladBrowsing.settings.domStorageEnabled = true
        saladBrowsing.settings.useWideViewPort = true
        saladBrowsing.settings.loadWithOverviewMode = true
        saladBrowsing.settings.setSupportMultipleWindows(true)
        saladBrowsing.webChromeClient = SaladWebChromeClient()
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