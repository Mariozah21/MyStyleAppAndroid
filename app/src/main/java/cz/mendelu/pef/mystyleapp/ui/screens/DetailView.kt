package cz.mendelu.pef.mystyleapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import cz.mendelu.pef.mystyleapp.data.Item
import cz.mendelu.pef.mystyleapp.navigation.Destination
import cz.mendelu.pef.mystyleapp.navigation.INavigationRouter
import cz.mendelu.pef.mystyleapp.ui.elements.BackArrowScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun DetailView(
    navigation: INavigationRouter,
    viewModel: FirestoreViewModel = getViewModel(),

    title: String,
    price: String,
    username: String
){
    BackArrowScreen(topBarTitle = "Detail View", onBackClick = { navigation.navBack() }) {
        DetailViewContent(
            paddingValues = it
            ,viewModel = viewModel,
            title,
            price,
            username)
    }
}

@Composable
fun DetailViewContent(
    paddingValues: PaddingValues,
    viewModel: FirestoreViewModel,
    title: String,
    price: String,
    username: String
) {
    Box(modifier = Modifier.padding(paddingValues)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            /*
            Image(
                painter = rememberImagePainter(image),
                contentDescription = "Item Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))

             */
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$price €",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = username,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}