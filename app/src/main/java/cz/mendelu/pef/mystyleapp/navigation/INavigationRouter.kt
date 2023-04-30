package cz.mendelu.pef.mystyleapp.navigation

interface INavigationRouter {
    fun returnBack()
    fun navToMainScreen()
    fun navToMyProfileScreen()
    fun navToChatsScreen()
/*
    fun navToSignInScreen()
    fun navToVerifyEmailScreen()
    fun navToSignUpScreen()
    fun navToForgotPasswordScreen()
 */
}