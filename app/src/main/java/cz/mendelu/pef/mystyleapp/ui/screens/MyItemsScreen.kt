package cz.mendelu.pef.mystyleapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cz.mendelu.pef.mystyleapp.R
import cz.mendelu.pef.mystyleapp.navigation.INavigationRouter
import cz.mendelu.pef.mystyleapp.ui.elements.BottomNavigation
import cz.mendelu.pef.mystyleapp.ui.elements.ItemCard
import cz.mendelu.pef.mystyleapp.ui.elements.MyItemCard
import org.koin.androidx.compose.getViewModel


@Composable
fun MyItemsScreen(
    navigation: INavigationRouter,
    navController: NavController,
    viewModel: FirestoreViewModel = getViewModel()
) {
    BottomNavigation(true,navigation,navController = navController, topBarTitle = stringResource(R.string.my_items_app_bar_title)) {
        MyItemsScreenContent(paddingValues = it, navigation = navigation ,viewModel = viewModel )
    }
}
@Composable
fun MyItemsScreenContent(
    paddingValues: PaddingValues,
    navigation: INavigationRouter,
    viewModel: FirestoreViewModel
) {
    val items = viewModel.myItems.value // Limit the number of items to 20

    Box(modifier = Modifier.fillMaxSize()) {
        if (items.isEmpty()) {
            Text(
                text = stringResource(R.string.my_items_you_are_not_selling_any_items),
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(items = items) { item ->
                    MyItemCard(item = item, navigation)
                }
            }
        }
    }
}
