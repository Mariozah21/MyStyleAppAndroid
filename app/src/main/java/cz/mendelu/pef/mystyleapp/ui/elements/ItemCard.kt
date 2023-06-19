package cz.mendelu.pef.mystyleapp.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cz.mendelu.pef.mystyleapp.data.Item
import coil.compose.rememberImagePainter
import cz.mendelu.pef.mystyleapp.navigation.INavigationRouter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemCard(
    item: Item,
    username: String,
    navigation: INavigationRouter) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = {
            navigation.navToDetailView(item.imageUrl,item.title,item.price.toString(),username)
        }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberImagePainter(item.imageUrl),
                contentDescription = "Item Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop,

            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = item.title,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${item.price} €",
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