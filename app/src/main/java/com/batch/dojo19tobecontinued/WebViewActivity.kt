package com.batch.dojo19tobecontinued

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val flag = intent.getStringExtra("FLAG")
        var url: String?
        val myWebView: WebView = findViewById(R.id.webview)

        if (flag == "gh") {
            url = intent.getStringExtra("GITHUBURL")
            myWebView.loadUrl(url.toString())
        } else if (flag == "tw") {
            url = intent.getStringExtra("TWITTERURL")
            myWebView.loadUrl(url.toString())
            finish()
        }
    }
}