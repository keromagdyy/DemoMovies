package com.cfh.demomovies.presentation.ui.splash.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cfh.demomovies.R
import com.cfh.demomovies.presentation.ui.theme.gray80
import com.cfh.demomovies.presentation.ui.theme.white
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    val splashTimeOut: Long = 5000
    var isTimeOut by remember { mutableStateOf(false) }
    val scale = remember { Animatable(0.5f) }

    LaunchedEffect(key1 = true) {
        launch {
            scale.animateTo(
                targetValue = 1.0f,
                animationSpec = tween(durationMillis = 1500)
            )
        }
        delay(splashTimeOut)
        isTimeOut = true
    }

    if (isTimeOut) {
        onTimeout()
    } else {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = gray80
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = stringResource(R.string.app_logo),
                    modifier = Modifier
                        .size(300.dp)
                        .scale(scale.value),
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = stringResource(R.string.powered_by),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 30.dp),
                    fontSize = 14.sp,
                    color = white
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(onTimeout = {})
}