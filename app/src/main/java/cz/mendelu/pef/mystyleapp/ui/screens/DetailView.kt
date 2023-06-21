package cz.mendelu.pef.mystyleapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
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
    id: String,
){
    val viewModel: DetailViewViewModel = remember { DetailViewViewModel(id) }

    BackArrowScreen(topBarTitle = "Detail View", onBackClick = { navigation.navBack() }) {
        DetailViewContent(
            paddingValues = it,
            id = id,
            viewModel = viewModel,
            )
    }
}

@Composable
fun DetailViewContent(
    paddingValues: PaddingValues,
    id: String,
    viewModel: DetailViewViewModel,

) {


    val item = viewModel.fetchedItemState.value


    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        if (item != null) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = rememberImagePainter(item.imageUrl),
                    contentDescription = "Item Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = item.title,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row() {
                    Text(text = "Price:")
                    Text(
                        text = "€ " + "${item.price}",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.fillMaxWidth().padding(start = 45.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                InfoElement(title = "Color" , content = item.color,45.dp)
                Spacer(modifier = Modifier.height(8.dp))
                if(item.stockCount == null ) {
                    InfoElement(title = "Stock", content = "Stock info is currently not available",45.dp)
                } else{
                    InfoElement(title = "Stock", content = item.stockCount.toString(),45.dp)
                }

                Spacer(modifier = Modifier.height(8.dp))
                InfoElement(title = "Size", content = item.size,48.dp)
                Spacer(modifier = Modifier.height(8.dp))
                InfoElement(title = "Description", content = item.description, 37.dp)

            }
        }
    }
}


@Composable
fun InfoElement(
    title: String,
    content: String?,
    paddingValue: Dp
){
    Row() {
        Text(text = "$title:")
        if(content == null){
            Text(
                text = "",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth().padding()
            )
        }else{
            Text(
                text = "${content}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth().padding(start = paddingValue)
            )

        }
    }
}