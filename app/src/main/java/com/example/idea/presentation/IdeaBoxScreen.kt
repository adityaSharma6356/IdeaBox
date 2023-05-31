package com.example.idea.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.idea.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun IdeaBoxScreen(mainViewModel: MainViewModel) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    BackHandler(query.isNotBlank() && !active) {
        query = ""
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        SearchBar(
            trailingIcon = {
                if(active){
                    if(query.isNotBlank()){
                        Icon(painter = painterResource(id = R.drawable.clear_icon), contentDescription = "", modifier = Modifier
                            .size(25.dp)
                            .clickable { query = "" })
                    }
                } else {
                    if(mainViewModel.user.profile!="") {
                        AsyncImage(
                            model = ImageRequest
                                .Builder(LocalContext.current)
                                .data(mainViewModel.user.profile)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )
                    } else {
                        Icon(painter = painterResource(id = R.drawable.profile), contentDescription = "", modifier = Modifier.size(25.dp))
                    }
                }
            },
            placeholder = {
                          Text(text = "Search Idea ,Domain or Topic", fontSize = 14.sp)
            },
            shape = RoundedCornerShape(50),
            query = query,
            onQueryChange =
            {
                query = it
                mainViewModel.loadSuggestions(query)
            },
            onSearch = {

            },
            active = active,
            onActiveChange = {active = it},
            content = {
                if(query.isBlank()){
                    Text(text = "Trending topics", fontSize = 20.sp, modifier = Modifier.padding(start = 20.dp, top = 5.dp, bottom = 20.dp))
                    LazyHorizontalStaggeredGrid(
                        horizontalItemSpacing = 5.dp,
                        rows = StaggeredGridCells.Fixed(9),
                        modifier = Modifier
                            .padding(10.dp)
                            .height(380.dp)
                            .fillMaxWidth()){
                        items(mainViewModel.trendingTopic.size){ index ->
                            Box(modifier = Modifier
                                .padding(vertical = 2.dp)
                                .height(40.dp)
                                .clickable {
                                    query = mainViewModel.trendingTopic[index]
                                    mainViewModel.loadSuggestions(query)
                                }
                                .clip(RoundedCornerShape(50))
                                .background(MaterialTheme.colorScheme.primary), contentAlignment = Alignment.Center) {
                                Text(text = mainViewModel.trendingTopic[index], fontSize = 14.sp, color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.padding(horizontal = 5.dp))
                            }
                        }
                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
                        items(mainViewModel.searchSuggestions.size){ index ->
                            Text(text = mainViewModel.searchSuggestions[index], fontSize = 16.sp, maxLines = 1, modifier = Modifier
                                .padding(start = 0.dp, top = 25.dp)
                                .fillMaxWidth(0.8f))
                        }
                    }
                }
            },
            modifier = Modifier,
            leadingIcon = {
                Crossfade(targetState = active) {
                    if(it){
                        Icon(
                            painter = painterResource(id = R.drawable.back_icon),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.clickable {
                                if(active){
                                    active = false
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
            }
        )
    }
}