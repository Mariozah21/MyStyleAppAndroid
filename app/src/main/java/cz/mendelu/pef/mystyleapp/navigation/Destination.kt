package cz.mendelu.pef.mystyleapp.navigation

sealed class Destination(val route: String) {
    object MainScreen : Destination(route = "main_screen")
    object MyProfileScreen : Destination(route = "my_profile")
    object WelcomeScreen :Destination(route = "welcome_screen")
    object AddItemScreen :Destination(route = "add_item_screen")
    object DetailView: Destination(route = "detail_view")
    object RegisterScreen : Destination(route = "register_screen")
    object MyItemsScreen : Destination(route = "my_items_screen")

    object SignInScreen: Destination(route = "sign_in_screen")
    object ForgotPasswordScreen: Destination(route = "forgot_password_screen")
    object SignUpScreen: Destination(route = "sign_up_screen")
    object VerifyEmailScreen: Destination(route = "verify_email_screen")
    
}