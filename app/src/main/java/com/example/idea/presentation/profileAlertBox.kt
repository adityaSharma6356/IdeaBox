package com.example.idea.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.idea.R
import com.example.idea.presentation.google.GoogleAuthUIClient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileAlertBox(
    mainViewModel: MainViewModel,
    navController: NavController,
    googleAuthUIClient: GoogleAuthUIClient
) {
    val config = LocalConfiguration.current
    AlertDialog(onDismissRequest = { mainViewModel.showProfileSection = false }, modifier = Modifier.width(config.screenWidthDp.dp)) {
        Card(
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp)),
            modifier = Modifier
                .height(500.dp)
                .fillMaxWidth()) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(60.dp)) {
                Icon(painter = painterResource(id = R.drawable.clear_icon), contentDescription = "", modifier = Modifier
                    .padding(start = 15.dp, top = 15.dp)
                    .size(25.dp)
                    .align(TopStart)
                    .clickable { mainViewModel.showProfileSection = false }
                )
                Text(text = "ideaBox", fontSize = 20.sp, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier
                    .padding(top = 10.dp)
                    .align(TopCenter)
                )
            }
            Card(
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .padding(bottom = 5.dp)
                    .fillMaxWidth(0.95f)
                    .height(350.dp)
                    .align(CenterHorizontally), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Row(
                    Modifier
                        .padding(top = 15.dp)
                        .fillMaxWidth()
                        .height(50.dp),
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (mainViewModel.state.user.profile != "") {
                        AsyncImage(
                            model = ImageRequest
                                .Builder(LocalContext.current)
                                .data(mainViewModel.state.user.profile)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 15.dp, end = 10.dp)
                                .size(40.dp)
                                .clip(CircleShape)
                        )
                        Column(
                            Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        ) {
                            Text(
                                text = mainViewModel.state.user.name,
                                fontSize = 15.sp,
                                maxLines = 1,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(top = 5.dp)
                            )
                            Text(
                                text = mainViewModel.state.user.email,
                                maxLines = 1,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Thin,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    }
                }
                if (mainViewModel.state.user.id == "") {
                    Spacer(modifier = Modifier.height(50.dp))
                    Text(
                        text = "Login or Register",
                        fontSize = 15.sp,
                        fontFamily = FontFamily.Monospace,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .align(CenterHorizontally)
                            .clickable {
                                navController.navigate("login")
                            }
                    )
                } else {
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedCard(
                        Modifier
                            .clickable {
                                mainViewModel.logout(googleAuthUIClient )
                            }
                            .height(IntrinsicSize.Min)
                            .align(CenterHorizontally)
                    ) {
                        Text(
                            text = "     Sign Out     ",
                            fontSize = 17.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(vertical = 5.dp).weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    Spacer(
                        modifier = Modifier.height(2.dp).fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp))
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(start = 18.dp, top = 110.dp)
                        .fillMaxWidth()
                        .height(40.dp), verticalAlignment = CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.settings_icon),
                        modifier = Modifier.size(25.dp),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "  Test Placeholder",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(start = 18.dp, top = 10.dp)
                        .fillMaxWidth()
                        .height(40.dp), verticalAlignment = CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.settings_icon),
                        modifier = Modifier.size(25.dp),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "  Profile Settings",
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            Row(modifier = Modifier
                .padding(start = 27.dp)
                .fillMaxWidth()
                .height(40.dp), verticalAlignment = CenterVertically
            ) {
                Icon(painter = painterResource(id = R.drawable.settings_icon), modifier = Modifier.size(25.dp), contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
                Text(text = "  Settings", fontSize = 15.sp ,color = MaterialTheme.colorScheme.onSurface )
            }
        }
    }
}
