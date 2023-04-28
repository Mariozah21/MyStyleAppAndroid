package cz.mendelu.pef.mystyleapp.navigation

import cz.mendelu.pef.mystyleapp.R

sealed class BottomNavItem(var title:String, var icon:Int, var route:String){

    object MainScreen : BottomNavItem("Main", R.drawable.ic_home,"home")
    object MyProfileScreen: BottomNavItem("My Profile",R.drawable.ic_account,"my_profile")
    object AddItemScreen: BottomNavItem("Post",R.drawable.ic_add,"add_item")
    object ChatsScreen: BottomNavItem("Chat",R.drawable.ic_chat,"chat")
    object SearchScreen: BottomNavItem("Search",R.drawable.ic_searchh,"search")
}
