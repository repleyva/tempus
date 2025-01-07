package com.repleyva.tempus.presentation.screens.settings

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.repleyva.tempus.R
import com.repleyva.tempus.domain.model.AppVersion
import com.repleyva.tempus.domain.model.DarkMode
import com.repleyva.tempus.domain.model.Timezone
import com.repleyva.tempus.presentation.common.ScreenTitleTextLarge
import com.repleyva.tempus.presentation.extensions.appVersion
import com.repleyva.tempus.presentation.extensions.darkMode
import com.repleyva.tempus.presentation.extensions.getAppVersionName
import com.repleyva.tempus.presentation.extensions.timeZone
import com.repleyva.tempus.presentation.screens.home.HomeViewModel
import com.repleyva.tempus.presentation.screens.settings.components.SettingsOptionItem
import com.repleyva.tempus.presentation.screens.settings.components.TimezoneDialog
import com.repleyva.tempus.presentation.screens.settings.components.UserSection
import com.repleyva.tempus.presentation.theme.Dimensions.paddingSemiMedium
import com.repleyva.tempus.presentation.theme.TempusTheme

@Composable
fun SettingsScreen(
    homeViewModel: HomeViewModel,
    showTimezoneDialog: Boolean,
    onShowTimezoneDialog: () -> Unit,
    onDismissTimezoneDialog: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
) {

    val theme by viewModel.theme.collectAsState()
    val nickname by viewModel.nickname.collectAsState()
    val selectedEmoji by viewModel.selectedEmoji.collectAsState(initial = "\uD83D\uDE36")
    var editableNickname by rememberSaveable { mutableStateOf(nickname) }
    val selectedTimezone by viewModel.selectedTimezone.collectAsState()

    val context = LocalContext.current

    SettingsScreenContent(
        modifier = modifier,
        darkMode = context.darkMode(isDarkModeEnabled = theme),
        timezone = context.timeZone(selectedTimezone = selectedTimezone),
        appVersion = context.appVersion(appVersion = context.getAppVersionName()),
        nickname = nickname,
        onNicknameChange = { newNickname ->
            editableNickname = newNickname
            viewModel.updateNickname(newNickname)
        },
        selectedEmoji = selectedEmoji,
        onEmojiChange = { newEmoji -> viewModel.updateSelectedEmoji(newEmoji) },
        onToggleDarkMode = { isChecked -> viewModel.updateDarkMode(isChecked) },
        selectedTimezone = selectedTimezone,
        onTimezoneChange = { timezone ->
            viewModel.updateTimezone(timezone)
            homeViewModel.updateCity()
        },
        onShowTimezoneDialog = onShowTimezoneDialog,
        onDismissTimezoneDialog = onDismissTimezoneDialog,
        showTimezoneDialog = showTimezoneDialog
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenContent(
    darkMode: DarkMode,
    timezone: Timezone,
    appVersion: AppVersion,
    nickname: String,
    onNicknameChange: (String) -> Unit,
    selectedEmoji: String,
    onEmojiChange: (String) -> Unit,
    onToggleDarkMode: (Boolean) -> Unit,
    selectedTimezone: String,
    onTimezoneChange: (String) -> Unit,
    onShowTimezoneDialog: () -> Unit,
    onDismissTimezoneDialog: () -> Unit,
    showTimezoneDialog: Boolean,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .testTag("SettingsScreenContent"),
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(paddingSemiMedium),
                title = { ScreenTitleTextLarge(textResId = R.string.settings) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) {

        LazyColumn(
            modifier = Modifier
                .padding(it)
                .testTag("SettingsList")
        ) {

            item {

                UserSection(
                    nickname = nickname,
                    onNicknameChange = onNicknameChange,
                    selectedEmoji = selectedEmoji,
                    onEmojiChange = onEmojiChange
                )

                SettingsOptionItem(
                    darkMode = darkMode,
                    timezone = timezone,
                    appVersion = appVersion,
                    onToggleDarkMode = onToggleDarkMode,
                    selectedTimezone = selectedTimezone,
                    onShowTimezoneDialog = onShowTimezoneDialog,
                )
            }
        }
    }

    if (showTimezoneDialog) {
        TimezoneDialog(
            onDismiss = onDismissTimezoneDialog,
            onSelectTimezone = onTimezoneChange
        )
    }
}


@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun SettingsScreenPreview() {
    TempusTheme {
        val context = LocalContext.current
        SettingsScreenContent(
            darkMode = context.darkMode(isDarkModeEnabled = isSystemInDarkTheme()),
            timezone = context.timeZone(selectedTimezone = "America/New_York"),
            appVersion = context.appVersion(appVersion = "1.0.0"),
            nickname = "John Doe",
            onNicknameChange = {},
            selectedEmoji = "\uD83D\uDE36",
            onEmojiChange = {},
            onToggleDarkMode = {},
            selectedTimezone = "America/New_York",
            onTimezoneChange = {},
            onShowTimezoneDialog = {},
            onDismissTimezoneDialog = {},
            showTimezoneDialog = false
        )
    }
}