package com.practicum.buyinglist

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.practicum.buyinglist.ui.theme.ToDoListTheme
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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Hello $name!",
        modifier = modifier.padding(16.dp),
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ToDoListTheme {
        Greeting("Android")
    }
}
