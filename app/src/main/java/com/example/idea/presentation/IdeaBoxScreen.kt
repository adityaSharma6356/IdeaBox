package com.example.idea.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.idea.R
import com.example.idea.util.SortBy

@Composable
fun IdeaBoxScreen(mainViewModel: MainViewModel) {
    Column(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectTapGestures { mainViewModel.openFilter = false }
        }, horizontalAlignment = Alignment.CenterHorizontally) {
        BackHandler(!mainViewModel.active && !mainViewModel.openFilter && (mainViewModel.currentSort != SortBy.LATEST || mainViewModel.currentDiff != SortBy.DIFFICULTY_RANDOM)) {
            mainViewModel.currentSort = SortBy.LATEST
            mainViewModel.currentDiff = SortBy.DIFFICULTY_RANDOM
            mainViewModel.sortSearchResults(mainViewModel.currentSort, mainViewModel.currentDiff)
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(colors = IconButtonDefaults.iconButtonColors(containerColor =  if (mainViewModel.searchFilters.isEmpty() && mainViewModel.currentSort == SortBy.LATEST && mainViewModel.currentDiff == SortBy.DIFFICULTY_RANDOM) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary), onClick = { mainViewModel.openFilter = !mainViewModel.openFilter }, modifier = Modifier
                .padding(start = 10.dp)
                .width(80.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, ) {
                    Icon(tint = if (mainViewModel.searchFilters.isEmpty() && mainViewModel.currentSort == SortBy.LATEST && mainViewModel.currentDiff == SortBy.DIFFICULTY_RANDOM) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onPrimary, painter = painterResource(id = if (mainViewModel.searchFilters.isEmpty() && mainViewModel.currentSort == SortBy.LATEST && mainViewModel.currentDiff == SortBy.DIFFICULTY_RANDOM ) R.drawable.filter_off_icon else R.drawable.filter_on_icon), contentDescription = null)
                    Text(text = "Filter", maxLines = 1, color = if (mainViewModel.searchFilters.isEmpty() && mainViewModel.currentSort == SortBy.LATEST && mainViewModel.currentDiff == SortBy.DIFFICULTY_RANDOM) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
        AnimatedVisibility(visible = mainViewModel.openFilter) {
            BackHandler(mainViewModel.openFilter) {
                mainViewModel.openFilter = false
            }
            Card(modifier = Modifier
                .fillMaxWidth(0.95f),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                        .height(40.dp)
                        .fillMaxWidth()) {
                    Text(text = "Sort By : ", fontSize = 15.sp)
                    IconButton(
                        modifier = Modifier.width(100.dp),
                        colors = IconButtonDefaults.iconButtonColors(containerColor =  if (mainViewModel.currentSort != SortBy.POPULAR) MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp) else MaterialTheme.colorScheme.primary),
                        onClick = {
                        mainViewModel.sortSearchResults(SortBy.POPULAR, mainViewModel.currentDiff)
                        mainViewModel.currentSort = SortBy.POPULAR
                    }) {
                        Text(text = "Popularity", maxLines = 1, fontSize = 15.sp, color =  if (mainViewModel.currentSort != SortBy.POPULAR) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onPrimary)
                    }
                    IconButton(
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .width(120.dp),
                        colors = IconButtonDefaults.iconButtonColors(containerColor =  if (mainViewModel.currentSort != SortBy.LATEST) MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp) else MaterialTheme.colorScheme.primary),
                        onClick = {
                        mainViewModel.sortSearchResults(SortBy.LATEST,  mainViewModel.currentDiff)
                        mainViewModel.currentSort = SortBy.LATEST
                    }) {
                        Text(text = "Upload Date", maxLines = 1, fontSize = 15.sp, color =  if (mainViewModel.currentSort != SortBy.LATEST) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onPrimary)
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp)
                        .height(40.dp)
                        .fillMaxWidth()) {
                    Text(text = "Difficulty : ", fontSize = 15.sp)
                    IconButton(
                        modifier = Modifier.width(100.dp),
                        colors = IconButtonDefaults.iconButtonColors(containerColor =  if (mainViewModel.currentDiff != SortBy.DIFFICULTY_BEGINNER) MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp) else MaterialTheme.colorScheme.primary),
                        onClick = {
                        mainViewModel.sortSearchResults(mainViewModel.currentSort, SortBy.DIFFICULTY_BEGINNER)
                        mainViewModel.currentDiff = SortBy.DIFFICULTY_BEGINNER
                    }) {
                        Text(text = "Beginner", maxLines = 1, fontSize = 15.sp, color =  if (mainViewModel.currentDiff != SortBy.DIFFICULTY_BEGINNER) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onPrimary)
                    }
                    IconButton(
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .width(120.dp),
                        colors = IconButtonDefaults.iconButtonColors(containerColor =  if (mainViewModel.currentDiff != SortBy.DIFFICULTY_INTERMEDIATE) MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp) else MaterialTheme.colorScheme.primary),
                        onClick = {
                            mainViewModel.sortSearchResults(mainViewModel.currentSort, SortBy.DIFFICULTY_INTERMEDIATE)
                            mainViewModel.currentDiff = SortBy.DIFFICULTY_INTERMEDIATE
                    }) {
                        Text(text = "Intermediate", maxLines = 1, fontSize = 15.sp, color =  if (mainViewModel.currentDiff != SortBy.DIFFICULTY_INTERMEDIATE) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onPrimary)
                    }
                    IconButton(
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .width(50.dp),
                        colors = IconButtonDefaults.iconButtonColors(containerColor =  if (mainViewModel.currentDiff != SortBy.DIFFICULTY_PRO) MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp) else MaterialTheme.colorScheme.primary),
                        onClick = {
                            mainViewModel.sortSearchResults(mainViewModel.currentSort, SortBy.DIFFICULTY_PRO)
                            mainViewModel.currentDiff = SortBy.DIFFICULTY_PRO
                    }) {
                        Text(text = "Pro", maxLines = 1, fontSize = 15.sp, color =  if (mainViewModel.currentDiff != SortBy.DIFFICULTY_PRO) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onPrimary)
                    }
                    IconButton(
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .width(100.dp),
                        colors = IconButtonDefaults.iconButtonColors(containerColor =  if (mainViewModel.currentDiff != SortBy.DIFFICULTY_RANDOM) MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp) else MaterialTheme.colorScheme.primary),
                        onClick = {
                            mainViewModel.sortSearchResults(mainViewModel.currentSort, SortBy.DIFFICULTY_RANDOM)
                            mainViewModel.currentDiff = SortBy.DIFFICULTY_RANDOM
                    }) {
                        Text(text = "Random", maxLines = 1, fontSize = 15.sp, color =  if (mainViewModel.currentDiff != SortBy.DIFFICULTY_RANDOM) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
        }
    }
}















