package cz.mendelu.pef.mystyleapp.ui.elements

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackArrowScreen(
    topBarTitle: String,
    onBackClick: () -> Unit,
    content: @Composable (paddingValues: PaddingValues) -> Unit
){
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = topBarTitle)
            },
            navigationIcon = {
                IconButton(onClick = { onBackClick() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "")
                }
            },
            )
    }) {
        LazyColumn(modifier = Modifier.padding(it)) {
            item {
                content(it)
            }
        }
    }
}