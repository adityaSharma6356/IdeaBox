package com.example.idea.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.idea.R
import com.example.idea.presentation.util.UiEvents


@Composable
fun MyIdeasScreen(
    mainViewModel: MainViewModel,
    navController: NavController,
    addProjectViewModel: AddProjectViewModel
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier
            .padding(end = 10.dp, top = 10.dp)
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "My " + if (mainViewModel.state.setView == 0) "Ideas" else "Bookmarks",
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(start = 15.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.surface),
                onClick = {
                          mainViewModel.onEvent(UiEvents.SetBookmarkView)
                },
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.bookmark_icon),
                    contentDescription = null,
                    tint = if(mainViewModel.state.setView==1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(25.dp)
                )
            }
            IconButton(
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.surface),
                onClick = {
                    mainViewModel.onEvent(UiEvents.SetMyIdeaView)
                },
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.idea),
                    contentDescription = null,
                    tint = if(mainViewModel.state.setView==0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(25.dp)
                )
            }
        }
        IdeasList(
            mainViewModel = mainViewModel,
            navController = navController,
            tempList = mainViewModel.state.secondList,
            addProjectViewModel
        )
    }
}