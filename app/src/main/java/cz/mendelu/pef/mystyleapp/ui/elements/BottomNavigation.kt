package cz.mendelu.pef.mystyleapp.ui.elements

import cz.mendelu.pef.mystyleapp.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cz.mendelu.pef.mystyleapp.navigation.BottomNavItem
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigation(
    navController: NavController,
    content: @Composable (paddingValues: PaddingValues) -> Unit){
    val items = listOf(
        BottomNavItem.MainScreen,
        BottomNavItem.SearchScreen,
        BottomNavItem.AddItemScreen,
        BottomNavItem.ChatsScreen,
        BottomNavItem.MyProfileScreen,
    )
    val coroutineScope = rememberCoroutineScope()

    Scaffold(bottomBar = {
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
                            LaunchedEffect(true){
                                navController.navigate(item.route)
                            }
                        }
                    }
                )
            }
        }

    }) {
        LazyColumn(modifier = Modifier.padding(it)) {
            item {
                content(it)
            }
        }
    }

}



    