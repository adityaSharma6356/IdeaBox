package com.example.idea.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.idea.R
import com.example.idea.presentation.util.SortBy
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun IdeaBoxScreen(mainViewModel: MainViewModel, navController: NavHostController) {
    Column(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectTapGestures { mainViewModel.openFilter = false }
        }, horizontalAlignment = Alignment.CenterHorizontally) {
        BackHandler(!mainViewModel.active && !mainViewModel.openFilter && (mainViewModel.state.currentSortBy != SortBy.LATEST || mainViewModel.state.currentDifficulty != SortBy.DIFFICULTY_RANDOM)) {
            mainViewModel.state.currentSortBy = SortBy.LATEST
            mainViewModel.state.currentDifficulty = SortBy.DIFFICULTY_RANDOM
            mainViewModel.sortSearchResults(mainViewModel.state.currentSortBy, mainViewModel.state.currentDifficulty)
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp), verticalAlignment = CenterVertically
        ) {
            IconButton(colors = IconButtonDefaults.iconButtonColors(containerColor =  if (mainViewModel.searchFilters.isEmpty() && mainViewModel.state.currentSortBy == SortBy.LATEST && mainViewModel.state.currentDifficulty == SortBy.DIFFICULTY_RANDOM) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary), onClick = { mainViewModel.openFilter = !mainViewModel.openFilter }, modifier = Modifier
                .padding(start = 10.dp)
                .width(80.dp)) {
                Row(verticalAlignment = CenterVertically) {
                    Icon(tint = if (mainViewModel.searchFilters.isEmpty() && mainViewModel.state.currentSortBy == SortBy.LATEST && mainViewModel.state.currentDifficulty == SortBy.DIFFICULTY_RANDOM) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onPrimary, painter = painterResource(id = if (mainViewModel.searchFilters.isEmpty() && mainViewModel.state.currentSortBy == SortBy.LATEST && mainViewModel.state.currentDifficulty == SortBy.DIFFICULTY_RANDOM ) R.drawable.filter_off_icon else R.drawable.filter_on_icon), contentDescription = null)
                    Text(text = "Filter", maxLines = 1, color = if (mainViewModel.searchFilters.isEmpty() && mainViewModel.state.currentSortBy == SortBy.LATEST && mainViewModel.state.currentDifficulty == SortBy.DIFFICULTY_RANDOM) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
        AnimatedVisibility(visible = mainViewModel.openFilter) {
            BackHandler(mainViewModel.openFilter) {
                mainViewModel.openFilter = false
            }
            Card(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .fillMaxWidth(0.95f),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                        3.dp
                    )
                )
            ) {
                Row(
                    verticalAlignment = CenterVertically,
                    modifier = Modifier
                        .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Sort By : ", fontSize = 15.sp)
                    IconButton(
                        modifier = Modifier.width(100.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = if (mainViewModel.state.currentSortBy != SortBy.POPULAR) MaterialTheme.colorScheme.surfaceColorAtElevation(
                                3.dp
                            ) else MaterialTheme.colorScheme.primary
                        ),
                        onClick = {
                            mainViewModel.sortSearchResults(
                                SortBy.POPULAR,
                                mainViewModel.state.currentDifficulty
                            )
                            mainViewModel.state.currentSortBy = SortBy.POPULAR
                        }) {
                        Text(
                            text = "Popularity",
                            maxLines = 1,
                            fontSize = 15.sp,
                            color = if (mainViewModel.state.currentSortBy != SortBy.POPULAR) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    IconButton(
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .width(80.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = if (mainViewModel.state.currentSortBy != SortBy.LATEST) MaterialTheme.colorScheme.surfaceColorAtElevation(
                                3.dp
                            ) else MaterialTheme.colorScheme.primary
                        ),
                        onClick = {
                            mainViewModel.sortSearchResults(
                                SortBy.LATEST,
                                mainViewModel.state.currentDifficulty
                            )
                            mainViewModel.state.currentSortBy = SortBy.LATEST
                        }) {
                        Text(
                            text = "Latest",
                            maxLines = 1,
                            fontSize = 15.sp,
                            color = if (mainViewModel.state.currentSortBy != SortBy.LATEST) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
                Row(
                    verticalAlignment = CenterVertically,
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp)
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Difficulty : ", fontSize = 15.sp)
                    IconButton(
                        modifier = Modifier.width(100.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = if (mainViewModel.state.currentDifficulty != SortBy.DIFFICULTY_BEGINNER) MaterialTheme.colorScheme.surfaceColorAtElevation(
                                3.dp
                            ) else MaterialTheme.colorScheme.primary
                        ),
                        onClick = {
                            mainViewModel.sortSearchResults(
                                mainViewModel.state.currentSortBy,
                                SortBy.DIFFICULTY_BEGINNER
                            )
                            mainViewModel.state.currentDifficulty = SortBy.DIFFICULTY_BEGINNER
                        }) {
                        Text(
                            text = "Beginner",
                            maxLines = 1,
                            fontSize = 15.sp,
                            color = if (mainViewModel.state.currentDifficulty != SortBy.DIFFICULTY_BEGINNER) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    IconButton(
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .width(120.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = if (mainViewModel.state.currentDifficulty != SortBy.DIFFICULTY_INTERMEDIATE) MaterialTheme.colorScheme.surfaceColorAtElevation(
                                3.dp
                            ) else MaterialTheme.colorScheme.primary
                        ),
                        onClick = {
                            mainViewModel.sortSearchResults(
                                mainViewModel.state.currentSortBy,
                                SortBy.DIFFICULTY_INTERMEDIATE
                            )
                            mainViewModel.state.currentDifficulty = SortBy.DIFFICULTY_INTERMEDIATE
                        }) {
                        Text(
                            text = "Intermediate",
                            maxLines = 1,
                            fontSize = 15.sp,
                            color = if (mainViewModel.state.currentDifficulty != SortBy.DIFFICULTY_INTERMEDIATE) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    IconButton(
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .width(50.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = if (mainViewModel.state.currentDifficulty != SortBy.DIFFICULTY_PRO) MaterialTheme.colorScheme.surfaceColorAtElevation(
                                3.dp
                            ) else MaterialTheme.colorScheme.primary
                        ),
                        onClick = {
                            mainViewModel.sortSearchResults(
                                mainViewModel.state.currentSortBy,
                                SortBy.DIFFICULTY_PRO
                            )
                            mainViewModel.state.currentDifficulty = SortBy.DIFFICULTY_PRO
                        }) {
                        Text(
                            text = "Pro",
                            maxLines = 1,
                            fontSize = 15.sp,
                            color = if (mainViewModel.state.currentDifficulty != SortBy.DIFFICULTY_PRO) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    IconButton(
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .width(100.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = if (mainViewModel.state.currentDifficulty != SortBy.DIFFICULTY_RANDOM) MaterialTheme.colorScheme.surfaceColorAtElevation(
                                3.dp
                            ) else MaterialTheme.colorScheme.primary
                        ),
                        onClick = {
                            mainViewModel.sortSearchResults(
                                mainViewModel.state.currentSortBy,
                                SortBy.DIFFICULTY_RANDOM
                            )
                            mainViewModel.state.currentDifficulty = SortBy.DIFFICULTY_RANDOM
                        }) {
                        Text(
                            text = "Random",
                            maxLines = 1,
                            fontSize = 15.sp,
                            color = if (mainViewModel.state.currentDifficulty != SortBy.DIFFICULTY_RANDOM) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
        val refreshState = rememberSwipeRefreshState(isRefreshing = mainViewModel.isRefreshing)
        SwipeRefresh(state = refreshState, onRefresh = { mainViewModel.refresh() }, modifier = Modifier.fillMaxSize()) {
            AnimatedVisibility(visible = mainViewModel.isFilterChanging, enter = fadeIn(animationSpec = TweenSpec(100)), exit = fadeOut(animationSpec = TweenSpec(100))) {
                IdeasList(mainViewModel = mainViewModel, navController = navController, mainViewModel.state.tempList)
            }
        }
    }
}















