package cz.mendelu.pef.mystyleapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.mystyleapp.R
import cz.mendelu.pef.mystyleapp.ui.components.Constants
import cz.mendelu.pef.mystyleapp.ui.elements.BottomNavigation
import cz.mendelu.pef.mystyleapp.ui.elements.ItemCard
import org.koin.androidx.compose.getViewModel
import com.ramcosta.composedestinations.annotation.Destination
import cz.mendelu.pef.mystyleapp.ui.elements.MyScaffold


@Destination
@Composable
fun MainScreen(
    navigator: DestinationsNavigator,
    viewModel: FirestoreViewModel = getViewModel(),
){
    MyScaffold(topBarTitle = stringResource(R.string.main_screen_app_bar_title), navigator = navigator, showBottomNavigation = true, showCartIcon = true) {
        MainScreenContent(paddingValues = it,navigator,viewModel = viewModel )
    }
    /*
    BottomNavigation(false,navigator, topBarTitle = stringResource(R.string.main_screen_app_bar_title)) {
        MainScreenContent(paddingValues = it,navigator,viewModel = viewModel )
    }*/
}
@Composable
fun MainScreenContent(
    paddingValues: PaddingValues,
    navigator: DestinationsNavigator,
    viewModel: FirestoreViewModel
) {
    val items = viewModel.itemsState.shuffled().take(20) // Limit the number of items to 20
    val filter = remember { mutableStateOf<String?>(null) }
    val selectedCategory = remember { mutableStateOf<String?>(null) }


    Box(modifier = Modifier.fillMaxSize()) {
        if (items.isEmpty()) {
            Text(
                text = stringResource(R.string.main_screen_you_are_not_selling_any_items),
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            LazyColumn() {
                item {
                    ScrollableButtonRow(selectedCategory)
                }
                items(items = items) { item ->
                    val username = remember(item.email) {
                        mutableStateOf("")
                    }
                    LaunchedEffect(item.email) {
                        username.value = viewModel.fetchUsernameByEmail(item.email) ?: ""
                    }
                    if (selectedCategory.value == null || item.category == selectedCategory.value) {
                        ItemCard(item, username.value, navigator)
                    }

                }
            }
        }
    }

}
@Composable
fun ScrollableButtonRow(selectedCategory: MutableState<String?>) {
    val buttonTitles = Constants.categories

    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(buttonTitles) { title ->
            Button(
                onClick = { if(selectedCategory.value == title){
                    selectedCategory.value = null
                    }else {
                        selectedCategory.value = title
                    }},
                modifier = Modifier.height(36.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor =
                    if (selectedCategory.value == title || selectedCategory.value == null) {
                        Color.Cyan
                    } else {
                        Color.LightGray
                    }
                )


            ) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
