package com.example.idea.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.idea.R
import com.example.idea.presentation.util.MultiSelect
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProjectScreen(mainViewModel: MainViewModel) {
    ProvideWindowInsets() {
        val addProjectViewModel = viewModel<AddProjectViewModel>()
        var categoriesList by remember {
            mutableStateOf(
                addProjectViewModel.categories.map {
                    MultiSelect(
                        name = it,
                        selected = false
                    )
                }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .navigationBarsWithImePadding(),
            horizontalAlignment = CenterHorizontally
        ) {
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
            TextField(
                colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent, focusedContainerColor = Color.Transparent),
                value = addProjectViewModel.name,
                onValueChange = {
                    addProjectViewModel.name = it
                },
                shape = RoundedCornerShape(5.dp),
                label = {
                    Text(text = "Name your Idea", fontSize = 12.sp)
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.idea),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = if(addProjectViewModel.name.isBlank()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.primary
                    )
                },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(0.9f)
            )
            Box(modifier = Modifier
                .heightIn(min = 50.dp)
                .fillMaxWidth(0.9f)
                .clickable {
                    addProjectViewModel.alertOpen = true
                }
                .border(
                    color = if (addProjectViewModel.categoriesFinal.isEmpty()) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.primary,
                    width = (0.5).dp,
                    shape = RoundedCornerShape(10.dp)
                )){
                if(addProjectViewModel.categoriesFinal.isEmpty()){
                    Text(text = "Select Categories", color = MaterialTheme.colorScheme.primary)
                }
                LazyVerticalGrid(columns = GridCells.Adaptive(50.dp)){
                    items(addProjectViewModel.categoriesFinal.size){ index ->
                        IconButton(onClick = {}) {

                        }
                    }
                }
            }
            if(addProjectViewModel.alertOpen){
                AlertDialog(onDismissRequest = { addProjectViewModel.alertOpen = false }) {
                    LazyColumn(modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight(0.7f)) {
                        items(categoriesList.size){ index ->
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)) {
                                Text(text = categoriesList[index].name, fontSize = 15.sp , modifier = Modifier.padding(start = 10.dp))
                                Checkbox(checked = categoriesList[index].selected, onCheckedChange = {categoriesList[index].selected = it}, modifier = Modifier.padding(end = 10.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}












