package cz.mendelu.pef.mystyleapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import cz.mendelu.pef.mystyleapp.ui.screens.AddItemScreen
import cz.mendelu.pef.mystyleapp.ui.screens.ChatScreen
import cz.mendelu.pef.mystyleapp.ui.screens.DetailView
import cz.mendelu.pef.mystyleapp.ui.screens.MainScreen
import cz.mendelu.pef.mystyleapp.ui.screens.MyProfileScreen
import cz.mendelu.pef.mystyleapp.ui.screens.MyItemsScreen
import cz.mendelu.pef.mystyleapp.ui.screens.SearchScreen
import cz.mendelu.pef.mystyleapp.ui.screens.WelcomeScreen
import cz.mendelu.pef.mystyleapp.ui.screens.RegisterScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    navigation: INavigationRouter = remember {
        NavigationRouterImpl(navController)
    },
    startDestination: String
){
    val firebaseAuth = FirebaseAuth.getInstance()
    var user by remember { mutableStateOf(firebaseAuth.currentUser) }

    DisposableEffect(Unit) {
        val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            user = firebaseAuth.currentUser
        }
        firebaseAuth.addAuthStateListener(authStateListener)
        onDispose {
            firebaseAuth.removeAuthStateListener(authStateListener)
        }
    }

    NavHost(
        navController = navController,
        startDestination = if (user != null) Destination.MainScreen.route else startDestination){
        //Welcome Screen
        composable(route = Destination.WelcomeScreen.route){
            WelcomeScreen(navigation = navigation)
        }
        //Navigation destinations
        composable(route = Destination.MainScreen.route){
            MainScreen(navigation,navController)
        }
        composable(route = Destination.MyProfileScreen.route){
            MyProfileScreen(navigation,navController)
        }
        composable(route = Destination.RegisterScreen.route){
            RegisterScreen()
        }
        composable(route = Destination.MyItemsScreen.route){
            MyItemsScreen(navigation,navController)
        }


        //Bottom Navigation Bar
        composable(route = BottomNavItem.MainScreen.route){
            MainScreen(navigation,navController)
        }
        composable(route =BottomNavItem.MyProfileScreen.route){
            MyProfileScreen(navigation,navController)
        }
        composable(route = Destination.AddItemScreen.route){
            AddItemScreen(navigation, navController)
        }
        composable(route = BottomNavItem.ChatsScreen.route){
            ChatScreen(navigation,navController)
        }
        composable(route = BottomNavItem.SearchScreen.route){
            SearchScreen(navigation,navController)
        }

        composable(route = Destination.DetailView.route + "/{id}" ,
        arguments = listOf(
            navArgument("id"){
                type = NavType.StringType
            }
            )
        ) {
                val id = it.arguments?.getString("id")
                // Use the item and username in the DetailView composable
                DetailView(
                    navigation = navigation,
                    id = id!!,
                )
            }
        }
}





