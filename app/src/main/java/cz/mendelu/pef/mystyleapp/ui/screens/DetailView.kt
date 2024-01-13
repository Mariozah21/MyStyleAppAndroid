package cz.mendelu.pef.mystyleapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import cz.mendelu.pef.mystyleapp.R
import cz.mendelu.pef.mystyleapp.ui.elements.BackArrowScreen
import org.koin.androidx.compose.getViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import cz.mendelu.pef.mystyleapp.ui.elements.MyScaffold
import cz.mendelu.pef.mystyleapp.ui.screens.mycart.CartViewModel

@Destination
@Composable
fun DetailView(
    navigator: DestinationsNavigator,
    id: String,
    imageUrl: String,
    title: String,
    price: Double,
    email: String,
    description: String?,
    stockCount: Int?,
    color: String,
    size: String,
    category: String,

){
    val viewModel: CartViewModel = getViewModel()
    MyScaffold(
        topBarTitle = stringResource(R.string.detail_view_app_bar_title),
        navigator = navigator,
        onBackClick = { navigator.popBackStack() },
        showBackArrow = true
    ) {
        DetailViewContent(
            paddingValues = it, id, imageUrl,title,price,email,description,stockCount, color,size,category, viewModel
        )
    }
    /*
    BackArrowScreen(topBarTitle = stringResource(R.string.detail_view_app_bar_title), onBackClick = { navigator.popBackStack() }) {
        DetailViewContent(
            paddingValues = it, id, imageUrl,title,price,email,description,stockCount, color,size,category, viewModel
            )
    }

     */
}

@Composable
fun DetailViewContent(
    paddingValues: PaddingValues,
    id: String,
    imageUrl: String,
    title: String,
    price: Double,
    email: String,
    description: String?,
    stockCount: Int?,
    color: String,
    size: String,
    category: String,
    viewModel: CartViewModel,

    ) {
    val context = LocalContext.current




    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = rememberImagePainter(imageUrl),
                    contentDescription = "Item Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row() {
                    Text(text = stringResource(R.string.detail_view_price))
                    Text(
                        text = "€ " + "${price}",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 45.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                InfoElement(title = stringResource(R.string.detail_view_color) , content = color,45.dp)
                Spacer(modifier = Modifier.height(8.dp))
                if(stockCount == null ) {
                    InfoElement(title = stringResource(R.string.detail_view_stock), content = stringResource(
                                            R.string.detail_view_stock_info_is_currently_not_available),45.dp)
                } else{
                    InfoElement(title = stringResource(R.string.detail_view_stock), content = stockCount.toString(),45.dp)
                }

                Spacer(modifier = Modifier.height(8.dp))
                InfoElement(title = stringResource(R.string.detail_view_size), content = size,48.dp)
                Spacer(modifier = Modifier.height(8.dp))
                InfoElement(title = stringResource(R.string.detail_view_description), content = description, 37.dp)
                Button(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    ,onClick = {
                        viewModel.addToCart( title, price, imageUrl, size)
                        Toast.makeText(context,"Item was added to cart", Toast.LENGTH_SHORT).show()
                    }
                )
                {
                    Text(text = stringResource(R.string.add_to_my_cart))
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding()
            )
        }else{
            Text(
                text = "${content}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = paddingValue)
            )

        }
    }
}