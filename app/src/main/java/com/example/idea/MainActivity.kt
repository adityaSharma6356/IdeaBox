package com.example.idea

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.idea.domain.models.User
import com.example.idea.presentation.MainScreen
import com.example.idea.presentation.MainViewModel
import com.example.idea.presentation.google.GoogleAuthUIClient
import com.example.idea.ui.theme.IdeaTheme
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUIClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val mainViewModel = viewModel<MainViewModel>()
            IdeaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var logginIn by remember { mutableStateOf(false) }
                    val navController = rememberNavController()
                    val data = googleAuthUiClient.getSignedInUser()

                    if(data != null){
                        mainViewModel.user = data
                        mainViewModel.loginSuccess = true
                    }

                    NavHost(navController = navController, startDestination = "login"){
                        navigation(
                            startDestination = "login_or_register",
                            route = "login"
                        ){
                            composable(route = "login_or_register"){
                                val darkTheme = isSystemInDarkTheme()
                                var icon = if(darkTheme) R.drawable.btn_google_dark_normal_xhdpi else R.drawable.btn_google_light_normal_xhdpi
                                val launcher = rememberLauncherForActivityResult(
                                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                                    onResult = { result ->
                                        logginIn = false
                                        if(result.resultCode == RESULT_OK) {
                                            lifecycleScope.launch {
                                                val signInResult = googleAuthUiClient.signInWithIntent(
                                                    intent = result.data ?: return@launch
                                                )
                                                mainViewModel.user = signInResult.data ?: User()
                                                navController.navigate("main"){
                                                    popUpTo("login") {
                                                        inclusive = true
                                                    }
                                                }
                                            }
                                        }
                                    }
                                )
                                LaunchedEffect(key1 = mainViewModel.user){
                                    if(mainViewModel.loginSuccess){
                                        navController.navigate("main"){
                                            popUpTo("login") {
                                                inclusive = true
                                            }
                                        }
                                    }
                                }
                                Box(contentAlignment = Alignment.Center, modifier = Modifier
                                    .fillMaxSize()) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Row(modifier = Modifier
                                            .shadow(2.dp)
                                            .height(50.dp)
                                            .width(260.dp)
                                            .clickable {
                                                mainViewModel.loginToGoogle(
                                                    googleAuthUiClient,
                                                    launcher
                                                )
                                                logginIn = true
//                                                navController.navigate("main"){
//                                                    popUpTo("login") {
//                                                        inclusive = true
//                                                    }
//                                                }
                                            }
                                            .background(
                                                if (darkTheme) Color(
                                                    66,
                                                    133,
                                                    244,
                                                    255
                                                ) else Color.White
                                            ),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Image( painter = rememberAsyncImagePainter(model = icon), contentDescription = null, modifier = Modifier.fillMaxHeight())
                                            Text(text = "Sign in with Google", color = if(darkTheme) Color.White else Color(0, 0, 0, 138), textAlign = TextAlign.Center, fontWeight = FontWeight.Bold , modifier = Modifier.weight(1f))
                                        }
                                        Spacer(modifier = Modifier.height(20.dp))
                                        Box(Modifier.height(50.dp)) {
                                            if(logginIn){
                                                CircularProgressIndicator()
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        navigation(startDestination = "main_idea",route = "main"){
                            composable(route = "main_idea"){
                                MainScreen(mainViewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}
















