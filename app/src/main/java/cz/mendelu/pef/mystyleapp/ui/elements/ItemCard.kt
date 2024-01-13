package cz.mendelu.pef.mystyleapp.ui.elements

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cz.mendelu.pef.mystyleapp.data.Item
import coil.compose.rememberImagePainter
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.mystyleapp.ui.screens.destinations.DetailViewDestination
import cz.mendelu.pef.mystyleapp.ui.screens.mycart.CartViewModel
import org.koin.androidx.compose.getViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemCard(
    item: Item,
    username: String,
    navigator: DestinationsNavigator,
    viewModel: CartViewModel = getViewModel()
) {

    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = {
            navigator.navigate(DetailViewDestination(
                id=item.id,
                imageUrl = item.imageUrl,
                title = item.title,
                price = item.price,
                email = item.email,
                description = item.description,
                stockCount = item.stockCount,
                color = item.color,
                size = item.size,
                category = item.category,
                ))
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
                Text(text = item.title, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))
                Text(
                        text = "€ " + "${item.price}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    viewModel.addToCart(
                        item.title,
                        item.price,
                        item.imageUrl,
                        item.size
                    )
                    Toast.makeText(context,"Item was added to cart", Toast.LENGTH_SHORT).show()
                }) {
                    Text(text = "Add to cart")
                }
            }
        }
    }







