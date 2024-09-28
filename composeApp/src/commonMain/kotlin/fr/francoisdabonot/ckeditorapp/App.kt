package fr.francoisdabonot.ckeditorapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import ckeditorapp.composeapp.generated.resources.Res
import ckeditorapp.composeapp.generated.resources.compose_multiplatform
import com.multiplatform.webview.jsbridge.WebViewJsBridge
import com.multiplatform.webview.jsbridge.rememberWebViewJsBridge
import com.multiplatform.webview.util.KLogSeverity
import com.multiplatform.webview.web.LoadingState
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.WebViewState
import com.multiplatform.webview.web.rememberWebViewNavigator
import com.multiplatform.webview.web.rememberWebViewStateWithHTMLFile

@Composable
@Preview
fun App() {
    MaterialTheme {
        val webViewState = rememberWebViewStateWithHTMLFile(
            fileName = "./editor.html"
        )
        val webViewNavigator = rememberWebViewNavigator()
        val jsBridge = rememberWebViewJsBridge(webViewNavigator)

        LaunchedEffect(Unit) {
            initWebView(webViewState)
            initJsBridge(jsBridge)
        }

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            val loadingState = webViewState.loadingState
            if (loadingState is LoadingState.Loading) {
                LinearProgressIndicator(
                    progress = loadingState.progress,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            WebView(
                state = webViewState,
                modifier = Modifier.fillMaxSize(),
                captureBackPresses = false,
                navigator = webViewNavigator,
                webViewJsBridge = jsBridge,
            )
        }
    }
}

fun initWebView(webViewState: WebViewState) {
    webViewState.webSettings.apply {
        zoomLevel = 1.0
        isJavaScriptEnabled = true
        logSeverity = KLogSeverity.Debug
        allowFileAccessFromFileURLs = true
        allowUniversalAccessFromFileURLs = true
        androidWebSettings.apply {
            isAlgorithmicDarkeningAllowed = true
            safeBrowsingEnabled = true
            allowFileAccess = true
        }
    }
}

suspend fun initJsBridge(webViewJsBridge: WebViewJsBridge) {
   // webViewJsBridge.register(GreetJsMessageHandler())
    //        EventBus.observe<NavigationEvent> {
//            Logger.d {
//                "Received NavigationEvent"
//            }
//        }
   /* FlowEventBus.events.filter { it is NavigationEvent }.collect {
        Logger.d {
            "Received NavigationEvent"
        }
    }*/
}
