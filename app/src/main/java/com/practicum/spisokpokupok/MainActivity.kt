package com.practicum.spisokpokupok

import android.animation.ObjectAnimator
import android.media.Image
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.practicum.spisokpokupok.ui.theme.ToDoListTheme
import com.practicum.spisokpokupok.navigation.AppNavHost
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

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

        suspend fun combineImages(
            await: Image,
            await1: Image,
        ): Image {
            TODO("Not yet implemented")
        }

        fun loadImage(name1: String): Image = TODO("Provide the return value")

        suspend fun loadAndCombine(
            name1: String,
            name2: String,
        ): Image =
            coroutineScope {
                val deferred1 = async { loadImage(name1) }
                val deferred2 = async { loadImage(name2) }
                combineImages(deferred1.await(), deferred2.await())
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
