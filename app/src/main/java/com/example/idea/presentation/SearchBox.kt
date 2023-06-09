package com.example.idea.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.idea.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SearchBox(mainViewModel: MainViewModel, modifier: Modifier = Modifier) {
    BackHandler(mainViewModel.query.isNotBlank() && !mainViewModel.active) {
        mainViewModel.query = ""
        mainViewModel.state = mainViewModel.state.copy(currentQuery = "")
        mainViewModel.state = mainViewModel.state.copy(tempList = mainViewModel.state.mainList)
    }
    Box(contentAlignment = Alignment.BottomCenter, modifier = modifier.heightIn(min = 100.dp).fillMaxWidth()) {
        SearchBar(
            trailingIcon = {
                if(mainViewModel.active){
                    if(mainViewModel.query.isNotBlank()){
                        Icon(painter = painterResource(id = R.drawable.clear_icon), contentDescription = "", modifier = Modifier
                            .size(25.dp)
                            .clickable { mainViewModel.query = "" })
                    }
                } else {
                    if(mainViewModel.state.user.profile!="") {
                        AsyncImage(
                            model = ImageRequest
                                .Builder(LocalContext.current)
                                .data(mainViewModel.state.user.profile)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .clickable {
                                    mainViewModel.showProfileSection = true
                                }
                        )
                    } else {
                        Icon(painter = painterResource(id = R.drawable.profile), contentDescription = "", modifier = Modifier
                            .size(25.dp)
                            .clickable { mainViewModel.showProfileSection = true })
                    }
                }
            },
            placeholder = {
                Text(text = "Search Idea ,Domain or Topic", fontSize = 14.sp)
            },
            shape = RoundedCornerShape(50),
            query = mainViewModel.query,
            onQueryChange =
            {
                mainViewModel.query = it
                mainViewModel.loadSuggestions(mainViewModel.query)
                mainViewModel.getProjectsByName(mainViewModel.query)
            },
            onSearch = {
                mainViewModel.state.currentQuery = mainViewModel.query
                mainViewModel.state.search()
                mainViewModel.active = false
            },
            active = mainViewModel.active,
            onActiveChange = {mainViewModel.active = it},
            content = {
                if(mainViewModel.query.isBlank()){
                    Text(text = "Trending topics", fontSize = 20.sp, modifier = Modifier.padding(start = 20.dp, top = 5.dp, bottom = 20.dp))
                    LazyHorizontalStaggeredGrid(
                        horizontalItemSpacing = 5.dp,
                        rows = StaggeredGridCells.Fixed(7),
                        modifier = Modifier
                            .padding(10.dp)
                            .height(300.dp)
                            .fillMaxWidth()){
                        items(mainViewModel.state.trendingTopic.size){ index ->
                            Box(modifier = Modifier
                                .padding(vertical = 2.dp)
                                .height(40.dp)
                                .clickable {
                                    mainViewModel.query = mainViewModel.state.trendingTopic[index]
                                    mainViewModel.loadSuggestions(mainViewModel.query)
                                    mainViewModel.state.currentQuery = mainViewModel.query
                                    mainViewModel.state.search()
                                    mainViewModel.active = false
                                }
                                .clip(RoundedCornerShape(50))
                                .background(MaterialTheme.colorScheme.primary), contentAlignment = Alignment.Center) {
                                Text(text = mainViewModel.state.trendingTopic[index], fontSize = 14.sp, color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.padding(horizontal = 5.dp))
                            }
                        }
                    }
                } else {
                    if(mainViewModel.searchSuggestions.isNotEmpty()){
                        Text(text = "Topics", fontSize = 18.sp, modifier = Modifier.padding(start = 20.dp, top = 5.dp, bottom = 0.dp))
                        LazyColumn(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
                            items(mainViewModel.searchSuggestions.size){ index ->
                                Text(text = mainViewModel.searchSuggestions[index], fontSize = 16.sp, maxLines = 1, modifier = Modifier
                                    .padding(start = 0.dp, top = 25.dp)
                                    .fillMaxWidth(0.8f)
                                    .clickable {
                                        mainViewModel.query = mainViewModel.searchSuggestions[index]
                                        mainViewModel.state.currentQuery = mainViewModel.query
                                        mainViewModel.state.search()
                                        mainViewModel.active = false
                                    })
                            }
                        }
                    }
                    if(mainViewModel.state.tempSearchList.isNotEmpty()){
                        Spacer(
                            modifier = Modifier
                                .padding(top = 15.dp, bottom = 10.dp)
                                .fillMaxWidth()
                                .height((0.5).dp)
                                .background(MaterialTheme.colorScheme.onSurface)
                        )
                        Text(text = "Projects", fontSize = 18.sp, modifier = Modifier.padding(start = 20.dp, top = 5.dp, bottom = 0.dp))
                        LazyColumn(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
                            items(mainViewModel.state.tempSearchList.size){ index ->
                                Text(text = mainViewModel.state.tempSearchList[index].name, fontSize = 16.sp, maxLines = 1, modifier = Modifier
                                    .padding(start = 0.dp, top = 25.dp)
                                    .fillMaxWidth(0.8f)
                                    .clickable {
                                        mainViewModel.query = mainViewModel.state.tempSearchList[index].name
                                        mainViewModel.state.currentQuery = mainViewModel.query
                                        mainViewModel.state.search()
                                        mainViewModel.active = false
                                    })
                            }
                        }
                    }
                }
            },
            modifier = Modifier,
            leadingIcon = {
                if(mainViewModel.active){
                    Icon(
                        painter = painterResource(id = R.drawable.back_icon),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.clickable {
                            if(mainViewModel.active){
                                mainViewModel.active = false
                            }
                        }
                    )
                }else {
                    Icon(
                        painter = painterResource(id = R.drawable.searc_icon),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        )
    }
}