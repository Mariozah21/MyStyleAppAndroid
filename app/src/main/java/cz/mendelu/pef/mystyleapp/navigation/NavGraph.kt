package cz.mendelu.pef.mystyleapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cz.mendelu.pef.mystyleapp.ui.screens.AddItemScreen
import cz.mendelu.pef.mystyleapp.ui.screens.ChatScreen
import cz.mendelu.pef.mystyleapp.ui.screens.MainScreen
import cz.mendelu.pef.mystyleapp.ui.screens.MyProfileScreen
import cz.mendelu.pef.mystyleapp.ui.screens.SearchScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    navigation: INavigationRouter = remember {
        NavigationRouterImpl(navController)
    },
    startDestination: String
){
    NavHost(navController = navController, startDestination = startDestination){
        //Navigation destinations
        composable(route = Destination.MainScreen.route){
            MainScreen(navigation)
        }
        composable(route = Destination.MyProfileScreen.route){
            MyProfileScreen(navigation,navController)
        }
        //Bottom Navigation Bar
        composable(route = BottomNavItem.MainScreen.route){
            MainScreen(navigation)
        }
        composable(route =BottomNavItem.MyProfileScreen.route){
            MyProfileScreen(navigation,navController)
        }
        composable(route = BottomNavItem.AddItemScreen.route){
            AddItemScreen(navigation)
        }
        composable(route = BottomNavItem.ChatsScreen.route){
            ChatScreen(navigation)
        }
        composable(route = BottomNavItem.SearchScreen.route){
            SearchScreen(navigation)
        }
    }


}
