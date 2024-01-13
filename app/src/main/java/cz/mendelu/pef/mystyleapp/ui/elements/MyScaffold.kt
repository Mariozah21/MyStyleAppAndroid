package cz.mendelu.pef.mystyleapp.ui.elements

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.res.painterResource
import cz.mendelu.pef.mystyleapp.ui.screens.destinations.AddItemScreenDestination
import cz.mendelu.pef.mystyleapp.ui.screens.destinations.ChatScreenDestination
import cz.mendelu.pef.mystyleapp.ui.screens.destinations.MainScreenDestination
import cz.mendelu.pef.mystyleapp.ui.screens.destinations.MyCartScreenDestination
import cz.mendelu.pef.mystyleapp.ui.screens.destinations.MyItemsScreenDestination
import cz.mendelu.pef.mystyleapp.ui.screens.destinations.MyProfileScreenDestination
import cz.mendelu.pef.mystyleapp.ui.screens.destinations.SearchScreenDestination
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.mystyleapp.R
import cz.mendelu.pef.mystyleapp.navigation.BottomNavItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScaffold(
    showBackArrow: Boolean = false,
    showCartIcon: Boolean = false,
    showBottomNavigation: Boolean = false,
    showFAB: Boolean = false,
    topBarTitle: String,
    navigator: DestinationsNavigator,
    onBackClick: () -> Unit = {},
    content: @Composable (paddingValues: PaddingValues) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = topBarTitle)
                },
                navigationIcon = {
                    if (showBackArrow) {
                        IconButton(onClick = { onBackClick() }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "")
                        }
                    }
                },
                actions = {
                    if (showCartIcon) {
                        IconButton(onClick = { navigator.navigate(MyCartScreenDestination) }) {
                            Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Cart")
                        }
                    }
                }
            )
        },
        bottomBar = {
            if (showBottomNavigation) {
                NavigationBar(
                    containerColor = colorResource(id = R.color.teal_700)
                ) {
                    listOf(
                        BottomNavItem.MainScreen,
                        BottomNavItem.SearchScreen,
                        BottomNavItem.MyItemsScreen,
                        //BottomNavItem.ChatsScreen,
                        BottomNavItem.MyProfileScreen,
                    ).forEach { item ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    painterResource(id = item.icon),
                                    contentDescription = item.title,
                                    modifier = Modifier.size(30.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = item.title,
                                )
                            },
                            selected = false,
                            onClick = {
                                coroutineScope.launch {
                                    when (item) {
                                        BottomNavItem.MainScreen -> navigator.navigate(
                                            MainScreenDestination
                                        )
                                        BottomNavItem.MyProfileScreen -> navigator.navigate(
                                            MyProfileScreenDestination
                                        )/*
                                        BottomNavItem.ChatsScreen -> navigator.navigate(
                                            ChatScreenDestination
                                        )
                                        */
                                        BottomNavItem.SearchScreen -> navigator.navigate(
                                            SearchScreenDestination
                                        )
                                        BottomNavItem.MyItemsScreen -> navigator.navigate(
                                            MyItemsScreenDestination
                                        )
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            content(innerPadding)
            if (showFAB) {
                FloatingActionButton(
                    onClick = { navigator.navigate(AddItemScreenDestination) },
                    modifier = Modifier
                        .align(androidx.compose.ui.Alignment.BottomEnd)
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
