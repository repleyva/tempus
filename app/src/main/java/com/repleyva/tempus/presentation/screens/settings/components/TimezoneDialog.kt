package com.repleyva.tempus.presentation.screens.settings.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.window.Dialog
import com.repleyva.tempus.domain.constants.Timezones.REGIONS
import com.repleyva.tempus.presentation.theme.Dimensions.borderSmall
import com.repleyva.tempus.presentation.theme.Dimensions.paddingMedium
import com.repleyva.tempus.presentation.theme.Dimensions.paddingSmall

@Composable
fun TimezoneDialog(
    onDismiss: () -> Unit,
    onSelectTimezone: (String) -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .padding(paddingMedium)
                .clip(MaterialTheme.shapes.medium)
                .border(borderSmall, MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.onSurface
        ) {

            LazyColumn(
                modifier = Modifier.padding(paddingMedium)
            ) {
                items(REGIONS) { timezone ->

                    Column {

                        Text(
                            text = timezone,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onSelectTimezone(timezone)
                                    onDismiss()
                                }
                                .padding(vertical = paddingSmall),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.tertiary
                        )

                        HorizontalDivider(color = MaterialTheme.colorScheme.surface)
                    }
                }
            }
        }
    }
}
