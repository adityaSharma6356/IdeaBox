package com.example.idea.presentation

import android.text.format.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.idea.presentation.mappers.colorProvider
import com.example.idea.presentation.mappers.toName

@Composable
fun SingleIdeaScreen(mainViewModel: MainViewModel, navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = mainViewModel.state.highlightProject.name,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(top = 30.dp, start = 20.dp)
        )
        Text(
            text = "by "+mainViewModel.state.highlightProject.authorName,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier
                .padding(top = 10.dp, start = 20.dp)
        )
        Text(
            text = "last modified : "+ DateFormat.format("dd MMMM yyyy  hh:ss", mainViewModel.state.highlightProject.dateCreated.toDate()),
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier
                .padding(top = 5.dp, start = 20.dp, end = 10.dp, bottom = 20.dp)
        )
        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .align(CenterHorizontally),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                    2.dp
                )
            )
        ) {
            Text(
                text = "Categories",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 5.dp)
            )
            Text(
                text = mainViewModel.state.highlightProject.categories.joinToString(", "),
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(top = 5.dp, start = 15.dp, end = 10.dp, bottom = 25.dp)
            )
            Text(
                text = "Difficulty ",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(top = 5.dp, start = 10.dp, end = 10.dp, bottom = 5.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = toName(mainViewModel.state.highlightProject.difficulty),
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(top = 5.dp, start = 15.dp, end = 10.dp, bottom = 10.dp)
                )
                Spacer(
                    modifier = Modifier
                        .size(15.dp)
                        .clip(CircleShape)
                        .background(colorProvider(mainViewModel.state.highlightProject.difficulty))
                )
            }
        }
        Text(
            text = "Description",
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .padding(top = 10.dp, start = 20.dp, end = 20.dp, bottom = 10.dp)
        )
        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(top = 0.dp, bottom = 20.dp)
                .align(CenterHorizontally),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                    2.dp
                )
            )
        ) {
            Text(
                text = mainViewModel.state.highlightProject.description,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
            )
        }
        Text(
            text = "Discussion",
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 10.dp)
        )
        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(top = 0.dp, bottom = 20.dp)
                .align(CenterHorizontally),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                    2.dp
                )
            )
        ) {
            Text(
                text = "Under Maintenance",
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
            )
        }
    }
}










