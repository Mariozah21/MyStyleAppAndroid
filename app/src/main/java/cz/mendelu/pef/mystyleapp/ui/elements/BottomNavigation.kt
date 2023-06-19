package cz.mendelu.pef.mystyleapp.ui.elements

import androidx.compose.foundation.layout.Box
import cz.mendelu.pef.mystyleapp.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cz.mendelu.pef.mystyleapp.navigation.BottomNavItem
import cz.mendelu.pef.mystyleapp.navigation.Destination
import cz.mendelu.pef.mystyleapp.navigation.INavigationRouter
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigation(
    showFAB: Boolean,
    navigation: INavigationRouter,
    navController: NavController,
    topBarTitle: String,
    content: @Composable (paddingValues: PaddingValues) -> Unit,
){
    val items = listOf(
        BottomNavItem.MainScreen,
        BottomNavItem.SearchScreen,
        BottomNavItem.MyItemsScreen,
        BottomNavItem.ChatsScreen,
        BottomNavItem.MyProfileScreen,
    )
    val coroutineScope = rememberCoroutineScope()



    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = topBarTitle)
            })
        },

        bottomBar = {
        NavigationBar(
            containerColor = colorResource(id = R.color.teal_700)
        ) {
            items.forEach{ item ->
                NavigationBarItem(
                    icon = {
                        Icon(painterResource(id = item.icon),
                            contentDescription = item.title,
                            modifier = Modifier.size(30.dp))
                    },
                    label = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(top = 6.dp)
                        ) {
                            Text(
                                text = item.title,

                            )}
                    },
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            when (item) {
                                BottomNavItem.MainScreen -> navigation.navToMainScreen()
                                BottomNavItem.MyProfileScreen -> navigation.navToMyProfileScreen()
                                BottomNavItem.ChatsScreen -> navigation.navToChatsScreen()
                                BottomNavItem.SearchScreen -> navigation.navToSearchScreen()
                                BottomNavItem.MyItemsScreen -> navigation.navToMyItemsScreen()
                            }
                        }
                    }
                )
            }
        }

    }){ innerPadding ->
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        content(innerPadding)
        if (showFAB) {
            FloatingActionButton(
                onClick = {  navigation.navToAddItemScreen()},
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        }
    }
}

}



    