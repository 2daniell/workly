package com.example.workly.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.workly.domain.sampleServices
import com.example.workly.screens.ClientHomeScreen
import com.example.workly.screens.CreateServiceScreen
import com.example.workly.screens.LoginScreen
import com.example.workly.screens.ProfileScreen
import com.example.workly.screens.ProviderHomeScreen
import com.example.workly.screens.ServiceDetailScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController)
        }

        composable("signup") {
            com.example.workly.screens.SignUpScreen(navController)
        }

        composable("client_home") {
            ClientHomeScreen(navController)
        }

        composable("provider_home") {
            ProviderHomeScreen(navController)
        }

        composable("create_service") {
            CreateServiceScreen(navController)
        }

        composable("profile") {
            ProfileScreen(navController)
        }

        composable(
            route = "service_detail/{serviceId}",
            arguments = listOf(navArgument("serviceId") { type = NavType.IntType })
        ) { backStackEntry ->
            val serviceId = backStackEntry.arguments?.getInt("serviceId") ?: 0
            val service = sampleServices.firstOrNull { it.id == serviceId } ?: sampleServices.first()
            ServiceDetailScreen(navController, service)
        }
    }
}
