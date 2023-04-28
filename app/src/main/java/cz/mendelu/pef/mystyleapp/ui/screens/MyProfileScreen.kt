package cz.mendelu.pef.mystyleapp.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import cz.mendelu.pef.mystyleapp.navigation.INavigationRouter
import cz.mendelu.pef.mystyleapp.ui.elements.BackArrowScreen
import cz.mendelu.pef.mystyleapp.ui.elements.BottomNavigation

@Composable
fun MyProfileScreen(
    navigation: INavigationRouter,
    navController: NavController
){
    BottomNavigation(navController = navController) {
        MyProfileScreenContent()
    }

}

@Composable
fun MyProfileScreenContent(){
    Text(text = "Hello world")
}