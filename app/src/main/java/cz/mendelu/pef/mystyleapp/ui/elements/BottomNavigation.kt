package cz.mendelu.pef.mystyleapp.ui.elements

import cz.mendelu.pef.mystyleapp.R
import android.R.id
import android.content.res.Resources
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import cz.mendelu.pef.mystyleapp.navigation.BottomNavItem


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
                    onClick = { navController.navigate(item.route) },

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

    