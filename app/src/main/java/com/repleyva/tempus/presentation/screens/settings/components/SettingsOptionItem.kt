package com.repleyva.tempus.presentation.screens.settings.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.vectorResource
import com.repleyva.tempus.domain.model.AppVersion
import com.repleyva.tempus.domain.model.DarkMode
import com.repleyva.tempus.domain.model.Timezone
import com.repleyva.tempus.presentation.common.GenericToggle
import com.repleyva.tempus.presentation.common.OptionDescriptionText
import com.repleyva.tempus.presentation.common.OptionTitleText
import com.repleyva.tempus.presentation.theme.Dimensions.iconExtraLarge
import com.repleyva.tempus.presentation.theme.Dimensions.paddingMedium
import com.repleyva.tempus.presentation.theme.Dimensions.paddingSmall

@Composable
fun SettingsOptionItem(
    darkMode: DarkMode,
    timezone: Timezone,
    appVersion: AppVersion,
    onToggleDarkMode: (Boolean) -> Unit,
    selectedTimezone: String,
    onShowTimezoneDialog: () -> Unit,
    modifier: Modifier = Modifier,
) {

    ItemSettings(
        icon = darkMode.icon,
        contentDescription = darkMode.title,
        text = darkMode.title,
        trailingContent = {
            GenericToggle(
                modifier = Modifier.testTag("DarkModeToggle"),
                isChecked = darkMode.isDarkModeEnabled,
                onCheckedChange = { isChecked ->
                    onToggleDarkMode(isChecked)
                }
            )
        },
    )

    ItemSettings(
        modifier = modifier.clickable { onShowTimezoneDialog() },
        icon = timezone.icon,
        contentDescription = timezone.title,
        text = timezone.title,
        supportingContent = {
            OptionDescriptionText(
                text = selectedTimezone,
                color = MaterialTheme.colorScheme.primary
            )
        },
    )

    ItemSettings(
        icon = appVersion.icon,
        contentDescription = appVersion.title,
        text = appVersion.title,
        supportingContent = {
            OptionDescriptionText(
                text = appVersion.description,
                color = MaterialTheme.colorScheme.secondary
            )
        },
    )
}

@Composable
private fun ItemSettings(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    contentDescription: String,
    text: String,
    supportingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
) {
    ListItem(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = paddingSmall, horizontal = paddingMedium),
        leadingContent = {
            Icon(
                imageVector = ImageVector.vectorResource(id = icon),
                contentDescription = contentDescription,
                modifier = Modifier.size(iconExtraLarge)
            )
        },
        headlineContent = { OptionTitleText(text = text) },
        supportingContent = supportingContent,
        trailingContent = trailingContent,
        colors = ListItemDefaults.colors(containerColor = Color.Transparent)
    )
}