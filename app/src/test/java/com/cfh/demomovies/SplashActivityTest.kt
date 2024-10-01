package com.cfh.demomovies

import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.cfh.demomovies.presentation.ui.moviesList.MoviesListActivity
import com.cfh.demomovies.presentation.ui.splash.ui.SplashScreen
import org.junit.Rule
import org.junit.Test

class SplashActivityTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testSplashScreen() {
        composeTestRule.setContent {
            val context = LocalContext.current
            SplashScreen {
                context.startActivity(Intent(context, MoviesListActivity::class.java))
            }
        }

        composeTestRule.onNodeWithTag("app_logo").assertExists()
        composeTestRule.onNodeWithTag("powered_by_text").assertExists()

        Thread.sleep(5000)
    }
}
