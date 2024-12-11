package com.practicum.spisokpokupok.root

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.practicum.spisokpokupok.navigation.AppNavHost
import com.practicum.spisokpokupok.ui.theme.ToDoListTheme
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import com.practicum.spisokpokupok.ui.theme.ToDoListTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setOnExitAnimationListener { screen ->
                val zoomX =
                    ObjectAnimator.ofFloat(
                        screen.iconView,
                        View.SCALE_X,
                        1.0f,
                        0.0f,
                    )
                val zoomY =
                    ObjectAnimator.ofFloat(
                        screen.iconView,
                        View.SCALE_Y,
                        0.8f,
                        0.0f,
                    )
                val set = android.animation.AnimatorSet()
                set.interpolator = OvershootInterpolator()
                set.play(zoomX).with(zoomY)
                set.duration = 500
                set.doOnEnd { screen.remove() }
                set.start()
            }
        }
        enableEdgeToEdge()
        setContent {
            ToDoListTheme {
                AppNavHost()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainPreview() {
    ToDoListTheme {
        AppNavHost()
    }
}
