package com.example.idea.util

import androidx.annotation.DrawableRes
import com.example.idea.R

sealed class Screen(val route: String,val label: String,@DrawableRes val icon: Int, @DrawableRes val offIcon: Int) {
    object Idea : Screen("idea_box", "IdeaBox", R.drawable.pngwing_com, R.drawable.pngwing_com)
    object MyIdea : Screen("my_ideas", "My Ideas", R.drawable.idea, R.drawable.idea_unselected)
    object Profile : Screen("profile", "Showcase", R.drawable.medal_icon_filled, R.drawable.medal_icon)
    object AddProject : Screen("add", "add", R.drawable.medal_icon_filled, R.drawable.medal_icon)
}
