package cz.mendelu.pef.mystyleapp.navigation

import cz.mendelu.pef.mystyleapp.data.Item


interface INavigationRouter {
    fun returnBack()
    fun navToMainScreen()
    fun navToMyProfileScreen()
    fun navToChatsScreen()
    fun navToWelcomeScreen()
    fun navToRegisterScreen()
    fun navToSearchScreen()
    fun navToAddItemScreen()
    fun navToMyItemsScreen()
    fun navToDetailView(image: String, title: String, price: String, username: String)
    fun navBack()


}