package com.global.tenantrix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import com.global.tenantrix.ui.navigation.AppNavGraph
import com.global.tenantrix.ui.theme.TenantrixTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        // 🔥 Install splash screen BEFORE setContent()
        val splash = installSplashScreen()

        super.onCreate(savedInstanceState)

        // 🔥 Add fade-in animation
        splash.setOnExitAnimationListener { splashView ->

            splashView.view.animate()
                .alpha(0f)          // fade to transparent
                .setDuration(400L)  // duration 400ms
                .withEndAction {
                    splashView.remove() // remove view after anim
                }
                .start()
        }

        enableEdgeToEdge()

        setContent {
            TenantrixTheme {
                AppNavGraph()
            }
        }
    }
}


