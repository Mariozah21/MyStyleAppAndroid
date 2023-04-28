package cz.mendelu.pef.mystyleapp.navigation

sealed class Destination(val route: String) {
    object MainScreen : Destination(route = "main_screen")
    object MyProfileScreen : Destination(route = "my_profile")
}