package com.example.idea.presentation

import android.text.format.DateFormat
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.idea.R
import com.example.idea.presentation.mappers.colorProvider
import com.example.idea.presentation.mappers.toName
import com.example.idea.presentation.util.Screen
import com.example.idea.presentation.util.SortBy
import com.example.idea.presentation.util.UiEvents
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
                Row(verticalAlignment = CenterVertically, ) {
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
                LazyColumn(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, contentPadding = PaddingValues(bottom = 100.dp)){
                    items(mainViewModel.state.tempList.size, key = {
                        mainViewModel.state.tempList[it].projectId
                    }){ index ->
                        OutlinedCard(
                            border = BorderStroke((0.5).dp, Color(255, 255, 255, 74)),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)),
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                                .clickable {
                                    navController.navigate(Screen.SingleIdea.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                                .fillMaxWidth(0.95f)
                                .heightIn(max = 250.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                val context = LocalContext.current
                                Text(
                                    text = mainViewModel.state.tempList[index].name,
                                    color = MaterialTheme.colorScheme.tertiary,
                                    fontSize = 20.sp,
                                    modifier = Modifier
                                        .padding(15.dp, 10.dp, 0.dp, 0.dp)
                                        .align(CenterVertically)
                                )
                                IconButton(onClick = {
                                    if(!mainViewModel.state.tempList[index].likedByUserId.contains(mainViewModel.state.user.id)){
                                        val tempdata = mainViewModel.state.tempList[index]
                                        tempdata.likedByUserId.add(mainViewModel.state.user.id)
                                        mainViewModel.onEvent(UiEvents.LikeProject(tempdata, context))
                                    } else {
                                        val tempdata = mainViewModel.state.tempList[index]
                                        tempdata.likedByUserId.remove(mainViewModel.state.user.id)
                                        mainViewModel.onEvent(UiEvents.LikeProject(tempdata, context))
                                    }
                                }, modifier = Modifier
                                    .padding(top = 5.dp)
                                    .size(70.dp, 40.dp)) {
                                    Row(verticalAlignment = CenterVertically) {
                                        Text(text = mainViewModel.state.tempList[index].likedByUserId.size.toString(), color = Color.Yellow, fontSize = 19.sp)
                                        Icon(painter = painterResource(
                                            id = if (mainViewModel.state.tempList[index].likedByUserId.contains(mainViewModel.state.user.id))
                                                R.drawable.starred_icon
                                            else
                                                R.drawable.not_starred_icon
                                        ),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .padding(horizontal = 5.dp)
                                                .size(25.dp),
                                            tint = Color.Yellow
                                        )
                                    }
                                }
                            }
                            val tempOP = MaterialTheme.colorScheme.secondary
                            var tempColor by remember {
                                mutableStateOf(colorProvider(mainViewModel.state.tempList[index].difficulty, tempOP))
                            }
                            Text(
                                text = " "+toName(mainViewModel.state.tempList[index].difficulty)+" ",
                                fontSize = 12.sp,
                                color = tempColor,
                                modifier = Modifier
                                    .padding(top = 5.dp, start = 15.dp)
                                    .border(1.dp, tempColor, RoundedCornerShape(50))
                                    .padding(3.dp),
                            )
                            val tempCatList = mainViewModel.state.tempList[index].categories
                            LazyRow(modifier = Modifier
                                .padding(start = 7.dp, top = 20.dp, end = 15.dp)
                                .fillMaxWidth(), verticalAlignment = CenterVertically
                            ){
                                itemsIndexed(tempCatList) { it, cate  ->
                                    Text(
                                        text = "  $cate  ",
                                        color = MaterialTheme.colorScheme.inverseSurface,
                                        modifier = Modifier
                                            .padding(horizontal = 5.dp)
                                            .background(
                                                shape = RoundedCornerShape(5.dp),
                                                color = MaterialTheme.colorScheme.surface
                                            ),
                                        fontSize = 12.sp
                                    )
                                    if(tempCatList.lastIndex>it){
                                        Spacer(
                                            modifier = Modifier
                                                .height(15.dp)
                                                .width((0.5).dp)
                                                .background(MaterialTheme.colorScheme.onSurface)
                                        )
                                    }
                                }
                            }
                            Text(
                                buildAnnotatedString {
                                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Light)){
                                        append("Posted on : ")
                                    }
                                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.onSurface)){
                                        append(DateFormat.format("dd MMMM yyyy  hh:ss", mainViewModel.state.tempList[index].dateCreated.toDate()) .toString())
                                    }
                                },
                                modifier = Modifier
                                    .padding(horizontal = 15.dp, vertical = 15.dp),
                                fontSize = 14.sp
                            )
                            Text(
                                text = mainViewModel.state.tempList[index].description,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .padding(15.dp, 0.dp, 15.dp, 10.dp)
                                    .background(
                                        shape = RoundedCornerShape(9.dp),
                                        color = MaterialTheme.colorScheme.surface
                                    )
                                    .padding(5.dp),
                                fontSize = 14.sp,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 3
                            )
                        }
                    }
                }
            }
        }
    }
}















