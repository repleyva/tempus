package com.repleyva.tempus.presentation.screens.explore.components


import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.repleyva.tempus.presentation.theme.TempusTheme

@Composable
fun ExploreCategory(
    modifier: Modifier = Modifier,
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 4.dp)
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        items(categories) { category ->
            OutlinedButton(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(0.8f),
                onClick = { onCategorySelected(category) },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor =
                    if (selectedCategory == category) MaterialTheme.colorScheme.surface
                    else Color.Transparent
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.surface)
            ) {
                Text(
                    text = category,
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ExploreCategoryPreview(
    categories: List<String> = listOf("Category", "Science", "Technology"),
    selectedCategory: String = "Category",
) {
    TempusTheme(
        dynamicColor = false
    ) {
        ExploreCategory(categories = categories, selectedCategory = selectedCategory, onCategorySelected = {})
    }

}