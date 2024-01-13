package cz.mendelu.pef.mystyleapp.navigation

import cz.mendelu.pef.mystyleapp.R

sealed class BottomNavItem(var title:String, var icon:Int, var route:String){

    object MainScreen : BottomNavItem("Main", R.drawable.ic_home,"main_screen")
    object MyProfileScreen: BottomNavItem("My Profile",R.drawable.ic_account,"my_profile_screen")
    object MyItemsScreen: BottomNavItem("My Items",R.drawable.ic_add,"my_items_screen")
    //object ChatsScreen: BottomNavItem("Chat",R.drawable.ic_chat,"chat")
    object SearchScreen: BottomNavItem("Search",R.drawable.ic_searchh,"search")
}
