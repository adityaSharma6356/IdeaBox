package com.example.idea.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.idea.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IdeaBoxScreen(mainViewModel: MainViewModel) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        SearchBar(
            trailingIcon = {
                           AsyncImage(
                               model = ImageRequest.Builder(LocalContext.current)
                                   .data(mainViewModel.user.profile).build(),
                               contentDescription = null,
                               modifier = Modifier
                                   .size(40.dp)
                                   .clip(CircleShape)
                                   .clickable { active = false }
                           )
            },
            placeholder = {
                          Text(text = "Search Idea ,Domain or Topic")
            },
            shape = RoundedCornerShape(50),
            query = query,
            onQueryChange = { query = it },
            onSearch = {

            },
            active = active,
            onActiveChange = {active = it},
            content = {

            },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(painter = painterResource(id = R.drawable.searc_icon), contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            }
        )
    }
}