package cz.mendelu.pef.mystyleapp.ui.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


val clothingTypes = listOf("T-Shirt", "Jeans", "Dress", "Sweater", "Shorts")


@Composable
fun CategoryDropdownMenu(onCategorySelected: (String) -> Unit) {
    val clothingTypes = listOf("T-Shirt", "Jeans", "Dress", "Sweater", "Shorts")
    var selectedClothingType by remember { mutableStateOf(clothingTypes[0]) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Select a clothing type:",
            style = MaterialTheme.typography.bodySmall
        )
        clothingTypes.forEach { clothingType ->
            Row(
                Modifier
                    .padding(vertical = 8.dp)
                    .selectable(
                        selected = (selectedClothingType == clothingType),
                        onClick = {
                            selectedClothingType = clothingType
                            onCategorySelected(clothingType) // Notify the selected category
                        }
                    )
            ) {
                RadioButton(
                    selected = (selectedClothingType == clothingType),
                    onClick = null
                )
                Text(
                    text = clothingType,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}




