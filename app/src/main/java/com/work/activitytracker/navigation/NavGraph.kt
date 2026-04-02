package com.work.activitytracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.work.activitytracker.feature.add.AddScreen
import com.work.activitytracker.feature.list.ListScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.List,
    ) {
        composable<Screen.List> {
            ListScreen(
                onNavigateToAdd = { navController.navigate(Screen.Add) },
            )
        }
        composable<Screen.Add> {
            AddScreen(
                onNavigateBack = { navController.popBackStack() },
            )
        }
    }
}
