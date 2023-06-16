package com.example.idea.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.idea.R
import com.example.idea.domain.models.ProjectIdea
import com.example.idea.presentation.mappers.toName
import com.example.idea.presentation.util.MultiSelect
import com.example.idea.presentation.util.Screen
import com.example.idea.presentation.util.SortBy
import com.example.idea.presentation.util.UiEvents
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.firebase.Timestamp


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AddProjectScreen(mainViewModel: MainViewModel, navController: NavHostController, addProjectViewModel: AddProjectViewModel) {
    ProvideWindowInsets() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .navigationBarsWithImePadding(),
            horizontalAlignment = CenterHorizontally
        ) {
//            var categoriesList by remember {
//                mutableStateOf(
//                    addProjectViewModel.categoriesList.map {
//                        MultiSelect(
//                            name = it,
//                            selected = false
//                        )
//                    }
//                )
//            }
            Text(
                text = "Drop your Idea",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(top = 15.dp, bottom = 5.dp)
                    .align(CenterHorizontally)
            )
            Spacer(modifier = Modifier
                .height((0.5).dp)
                .fillMaxWidth(0.95f)
                .background(MaterialTheme.colorScheme.onSurface)
                .align(CenterHorizontally))
            OutlinedTextField(
                colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent, focusedContainerColor = Color.Transparent),
                value = addProjectViewModel.name,
                onValueChange = {
                    addProjectViewModel.name = it
                },
                label = {
                    Text(text = "Name your Idea", fontSize = 12.sp)
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.idea),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = if(addProjectViewModel.name.isBlank()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.primary
                    )
                },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(0.9f)
            )
            Box(modifier = Modifier
                .heightIn(min = 50.dp, max = 200.dp)
                .fillMaxWidth(0.9f)
                .clickable {
                    val temp = addProjectViewModel.cp.toList()
                    temp.forEach {
                        addProjectViewModel.categoriesFinal.forEach { item ->
                            if (it.name==item.name){
                                it.selected = true
                            }
                        }
                    }
                    addProjectViewModel.cp.clear()
                    addProjectViewModel.cp.addAll(temp)
                    addProjectViewModel.alertOpen = true
                }
                .border(
                    color = if (addProjectViewModel.categoriesFinal.isEmpty()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.primary,
                    width = (0.5).dp,
                    shape = RoundedCornerShape(3.dp)
                )){
                if(addProjectViewModel.categoriesFinal.isEmpty()){
                    Text(text = "Select Categories", color = MaterialTheme.colorScheme.primary, modifier = Modifier.align(Center))
                }
                LazyHorizontalStaggeredGrid(rows = StaggeredGridCells.Adaptive(30.dp), contentPadding = PaddingValues(vertical = 10.dp) ){
                    items(addProjectViewModel.categoriesFinal.size, key = {
                        addProjectViewModel.categoriesFinal[it].name
                    }){ index ->
                        Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier
                            .padding(5.dp)
                            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(50))
                            .height(30.dp)){
                            Text(
                                text = addProjectViewModel.categoriesFinal[index].name,
                                modifier = Modifier.padding(horizontal = 5.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 13.sp
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.clear_icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .clickable {
                                        addProjectViewModel.cp[addProjectViewModel.categoriesFinal[index].selectorIndex].selected = false
                                        addProjectViewModel.categoriesFinal.removeAt(index)
                                    }
                                    .padding(end = 5.dp)
                                    .size(20.dp),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }
            ExposedDropdownMenuBox(modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(0.9f),
                expanded = addProjectViewModel.menuExpanded,
                onExpandedChange = {
                    addProjectViewModel.menuExpanded = !addProjectViewModel.menuExpanded
                }) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    readOnly = true,
                    label = { Text(text = "Difficulty")},
                    value = toName(addProjectViewModel.difficulty),
                    onValueChange = {},
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = addProjectViewModel.menuExpanded)
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(expanded = addProjectViewModel.menuExpanded, onDismissRequest = { addProjectViewModel.menuExpanded = false}) {
                    DropdownMenuItem(
                        text = { Text(text = "Beginner") },
                        onClick = {
                            addProjectViewModel.difficulty = SortBy.DIFFICULTY_BEGINNER
                            addProjectViewModel.menuExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Intermediate") },
                        onClick = {
                            addProjectViewModel.difficulty = SortBy.DIFFICULTY_INTERMEDIATE
                            addProjectViewModel.menuExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Pro") },
                        onClick = {
                            addProjectViewModel.difficulty = SortBy.DIFFICULTY_PRO
                            addProjectViewModel.menuExpanded = false
                        }
                    )
                }
            }
            OutlinedTextField(
                value = addProjectViewModel.description,
                onValueChange = { addProjectViewModel.description = it },
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(0.9f),
                label = { Text(text = "Tell us what your Idea is about", fontSize = 12.sp)}
            )
            TextButton(
                modifier = Modifier.padding(top = 30.dp, start = 10.dp).align(Start),
                onClick = {
                    addProjectViewModel.name = ""
                    addProjectViewModel.description = ""
                    addProjectViewModel.categoriesFinal.clear()
                    addProjectViewModel.difficulty = SortBy.DIFFICULTY_BEGINNER
                    addProjectViewModel.cp.clear()
                    addProjectViewModel.cp =  addProjectViewModel.categoriesList.map { MultiSelect(it, false) }.toMutableStateList()
            }) {
                Text(text = "Clear")
            }
            if(addProjectViewModel.alertOpen){
                AlertDialog(
                    properties = DialogProperties(usePlatformDefaultWidth = true),
                    modifier = Modifier
                        .height(600.dp)
                    ,onDismissRequest = {
                        addProjectViewModel.alertOpen = false
                        addProjectViewModel.cp.clear()
                        addProjectViewModel.cp =  addProjectViewModel.categoriesList.map { MultiSelect(it, false) }.toMutableStateList()
                    }
                ) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(addProjectViewModel.cp.size, key = {addProjectViewModel.cp[it].name}){ index ->
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)) {
                                Text(text = addProjectViewModel.cp[index].name, fontSize = 15.sp , maxLines = 1, overflow = TextOverflow.Ellipsis, modifier = Modifier.weight(1f))
                                Checkbox(checked = addProjectViewModel.cp[index].selected, onCheckedChange = {
                                    if(!addProjectViewModel.cp[index].selected){
                                        addProjectViewModel.cp[index] = addProjectViewModel.cp[index].copy(selected = true)
                                        addProjectViewModel.categoriesFinal.add(addProjectViewModel.CateBoxState(addProjectViewModel.cp[index].name, index))
                                    } else {
                                        addProjectViewModel.cp[index] = addProjectViewModel.cp[index].copy(selected = false)
                                        addProjectViewModel.categoriesFinal.removeIf { it.name == addProjectViewModel.cp[index].name }
                                    }
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}












