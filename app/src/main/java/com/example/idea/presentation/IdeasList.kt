package com.example.idea.presentation

import android.text.format.DateFormat
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.idea.R
import com.example.idea.domain.models.ProjectIdea
import com.example.idea.presentation.mappers.colorProvider
import com.example.idea.presentation.mappers.toName
import com.example.idea.presentation.util.Screen
import com.example.idea.presentation.util.UiEvents

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IdeasList(mainViewModel: MainViewModel, navController: NavController, tempList: MutableList<ProjectIdea>){
    LazyColumn(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(bottom = 100.dp)
    ){
        items(tempList.size, key = {
            tempList[it].projectId
        }){ index ->
            var showAlert by remember { mutableStateOf(false) }
            if(showAlert){
                AlertDialog(onDismissRequest = { showAlert = false }) {
                    Card(modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(200.dp)){
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .clickable {
                                    mainViewModel.onEvent(UiEvents.DeleteIdea(tempList[index]))
                                    showAlert = false
                                }
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(MaterialTheme.colorScheme.primary)) {
                            Text(text = "Delete Idea", fontSize = 20.sp, color = MaterialTheme.colorScheme.onPrimary)
                        }
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .clickable {
                                    showAlert = false
                                }
                            .fillMaxWidth()
                            .height(150.dp)
                            .background(MaterialTheme.colorScheme.surface)) {
                            Text(text = "Cancel", fontSize = 20.sp, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            }
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
                    .heightIn(max = 260.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = tempList[index].name,
                        color = MaterialTheme.colorScheme.tertiary,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(15.dp, 10.dp, 0.dp, 0.dp)
                            .align(Alignment.CenterVertically)
                    )
//                    IconButton(onClick = {
//                        if(!mainViewModel.state.tempList[index].likedByUserId.contains(mainViewModel.state.user.id)){
//                            val tempdata = mainViewModel.state.tempList[index]
//                            tempdata.likedByUserId.add(mainViewModel.state.user.id)
//                            mainViewModel.onEvent(UiEvents.LikeProject(tempdata, context))
//                        } else {
//                            val tempdata = mainViewModel.state.tempList[index]
//                            tempdata.likedByUserId.remove(mainViewModel.state.user.id)
//                            mainViewModel.onEvent(UiEvents.LikeProject(tempdata, context))
//                        }
//                    }, modifier = Modifier
//                        .padding(top = 5.dp)
//                        .size(70.dp, 40.dp)) {
//                        Row(verticalAlignment = Alignment.CenterVertically) {
//                            Text(
//                                text = mainViewModel.state.tempList[index].likedByUserId.size.toString(),
//                                color = Color.Yellow,
//                                fontSize = 19.sp,
//                            )
//                            Icon(painter = painterResource(
//                                id = if (mainViewModel.state.tempList[index].likedByUserId.contains(mainViewModel.state.user.id))
//                                    R.drawable.starred_icon
//                                else
//                                    R.drawable.not_starred_icon
//                            ),
//                                contentDescription = null,
//                                modifier = Modifier
//                                    .padding(horizontal = 5.dp)
//                                    .size(25.dp),
//                                tint = Color.Yellow
//                            )
//                        }
//                    }
                }
                val tempOP = MaterialTheme.colorScheme.secondary
                val tempColor by remember {
                    mutableStateOf(colorProvider(tempList[index].difficulty, tempOP))
                }
                Row(modifier = Modifier
                    .padding(end = 10.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = toName(tempList[index].difficulty),
                        fontSize = 12.sp,
                        color = tempColor,
                        modifier = Modifier
                            .padding(top = 5.dp, start = 15.dp)
                            .border(1.dp, tempColor, RoundedCornerShape(50))
                            .shadow(
                                10.dp,
                                spotColor = tempColor,
                                shape = RoundedCornerShape(50)
                            )
                            .padding(horizontal = 9.dp, vertical = 3.dp),
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    var contains by remember { mutableStateOf(tempList[index].bookMarkedByUsers.contains(mainViewModel.state.user.id)) }
                    if(tempList[index].author==mainViewModel.state.user.id){
                        IconButton(
                            colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.surface),
                            onClick = { /*TODO*/ },
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.edit_icon),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        IconButton(
                            colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.surface),
                            onClick = { showAlert = true },
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.delete_icon),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    IconButton(
                        colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.surface),
                        onClick = {
                            if(mainViewModel.state.user.id.isNotBlank()){
                                contains = if(contains){
                                    tempList[index].bookMarkedByUsers.remove(mainViewModel.state.user.id)
                                    mainViewModel.onEvent(UiEvents.Bookmark(tempList[index]))
                                    mainViewModel.onEvent(UiEvents.ShowBar("Bookmark Removed"))
                                    false
                                } else {
                                    tempList[index].bookMarkedByUsers.add(mainViewModel.state.user.id)
                                    mainViewModel.onEvent(UiEvents.Bookmark(tempList[index]))
                                    mainViewModel.onEvent(UiEvents.ShowBar("Bookmark Added"))
                                    true
                                }
                            } else {
                                mainViewModel.onEvent(UiEvents.ShowBar("Login required"))
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.bookmark_icon),
                            contentDescription = null,
                            tint = if(contains) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                val tempCatList = tempList[index].categories
                LazyRow(modifier = Modifier
                    .padding(start = 7.dp, top = 20.dp, end = 15.dp)
                    .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
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
                            append(DateFormat.format("dd MMMM yyyy  hh:ss", tempList[index].dateCreated.toDate()) .toString())
                        }
                    },
                    modifier = Modifier
                        .padding(horizontal = 15.dp, vertical = 15.dp),
                    fontSize = 14.sp
                )
                Text(
                    text = tempList[index].description,
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