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

    override fun navToWelcomeScreen() {
        navController.navigate(Destination.WelcomeScreen.route)
    }

    override fun navToRegisterScreen() {
        navController.navigate(Destination.RegisterScreen.route)
    }

    override fun navToSearchScreen() {
        navController.navigate(BottomNavItem.SearchScreen.route)
    }

    override fun navToAddItemScreen() {
        navController.navigate(Destination.AddItemScreen.route)
    }

    override fun navToMyItemsScreen() {
        navController.navigate(Destination.MyItemsScreen.route)
    }

    override fun navToDetailView(id: String) {
        navController.navigate(Destination.DetailView.route + "/" + id)
    }

    override fun navBack() {
        navController.popBackStack()
    }
}