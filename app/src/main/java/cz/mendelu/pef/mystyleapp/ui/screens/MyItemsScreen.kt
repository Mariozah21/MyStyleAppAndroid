package cz.mendelu.pef.mystyleapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.mystyleapp.R
import cz.mendelu.pef.mystyleapp.ui.elements.BottomNavigation
import cz.mendelu.pef.mystyleapp.ui.elements.MyItemCard
import cz.mendelu.pef.mystyleapp.ui.elements.MyScaffold
import org.koin.androidx.compose.getViewModel

@Destination
@Composable
fun MyItemsScreen(
    navigator: DestinationsNavigator,
    viewModel: FirestoreViewModel = getViewModel()
) {
    MyScaffold(topBarTitle = stringResource(R.string.my_items_app_bar_title), navigator = navigator, showFAB = true, showBottomNavigation = true, showCartIcon = true) {
        MyItemsScreenContent(paddingValues = it, navigator ,viewModel = viewModel )
    }
    /*
    BottomNavigation(true,navigator, topBarTitle = stringResource(R.string.my_items_app_bar_title)) {
        MyItemsScreenContent(paddingValues = it, navigator ,viewModel = viewModel )
    }

     */
}
@Composable
fun MyItemsScreenContent(
    paddingValues: PaddingValues,
    navigator: DestinationsNavigator,
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
                    MyItemCard(item = item, navigator)
                }
            }
        }
    }
}
