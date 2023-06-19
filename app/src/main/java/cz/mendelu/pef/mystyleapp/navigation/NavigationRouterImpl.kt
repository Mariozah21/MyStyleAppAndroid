package cz.mendelu.pef.mystyleapp.navigation

import androidx.navigation.NavController
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import cz.mendelu.pef.mystyleapp.data.Item
import cz.mendelu.pef.mystyleapp.data.JsonItem

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

    override fun navToDetailView(image: String, title: String, price: String, username: String) {
        val moshi: Moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<JsonItem> =
            moshi.adapter(JsonItem::class.java)
        val jsonString = jsonAdapter.toJson(JsonItem(title,price,username))

        navController.navigate(Destination.DetailView.route + "/" + jsonString)
    }

    override fun navBack() {
        navController.popBackStack()
    }
}