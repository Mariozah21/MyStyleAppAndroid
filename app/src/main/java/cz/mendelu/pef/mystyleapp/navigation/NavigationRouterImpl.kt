package cz.mendelu.pef.mystyleapp.navigation

import androidx.navigation.NavController

class NavigationRouterImpl(
    private val navController: NavController) : INavigationRouter {
    override fun returnBack() {
        navController.popBackStack()
    }

    override fun navToMainScreen() {
        navController.navigate(BottomNavItem.MainScreen.route)
    }

    override fun navToMyProfileScreen() {
        navController.navigate(BottomNavItem.MyProfileScreen.route)
    }

    override fun navToChatsScreen() {
        navController.navigate(BottomNavItem.ChatsScreen.route)
    }
}