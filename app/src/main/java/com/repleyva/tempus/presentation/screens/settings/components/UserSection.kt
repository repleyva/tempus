package com.repleyva.tempus.presentation.screens.settings.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import com.repleyva.tempus.R
import com.repleyva.tempus.domain.constants.Constants
import com.repleyva.tempus.presentation.theme.Dimensions.iconExtraLarge
import com.repleyva.tempus.presentation.theme.Dimensions.nicknameInputHeight
import com.repleyva.tempus.presentation.theme.Dimensions.paddingExtraSmall
import com.repleyva.tempus.presentation.theme.Dimensions.paddingMedium
import com.repleyva.tempus.presentation.theme.Dimensions.paddingNormal
import com.repleyva.tempus.presentation.theme.Dimensions.paddingSmall
import com.repleyva.tempus.presentation.theme.TempusTheme

@Composable
fun UserSection(
    nickname: String,
    onNicknameChange: (String) -> Unit,
    selectedEmoji: String,
    onEmojiChange: (String) -> Unit,
) {

    val isEditing = remember { mutableStateOf(false) }

    val editableNickname = remember { mutableStateOf(nickname) }

    LaunchedEffect(nickname) {
        editableNickname.value = nickname
    }

    val emojiIndex = remember { mutableIntStateOf(Constants.emojis.indexOf(selectedEmoji)) }

    val focusManager = LocalFocusManager.current

    val keyboardController = LocalSoftwareKeyboardController.current

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(isEditing.value) {
        if (isEditing.value) {
            focusRequester.requestFocus()
        }
    }

    val animatedTextColor by animateFloatAsState(targetValue = if (isEditing.value) 0.8f else 0.4f, label = "")

    val enterNicknameText = stringResource(id = R.string.enter_nickname)

    BackHandler(enabled = isEditing.value) {
        isEditing.value = false
        editableNickname.value = ""
        keyboardController?.hide()
    }

    val constraints = ConstraintSet {
        val emoji = createRefFor("emoji")
        val box = createRefFor("box")

        constrain(emoji) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(box) {
            top.linkTo(emoji.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }

    ConstraintLayout(
        constraintSet = constraints,
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingMedium)
            .offset(y = (12).dp)
    ) {
        UserSectionBody(
            editableNickname = editableNickname,
            onNicknameChange = onNicknameChange,
            isEditing = isEditing,
            enterNicknameText = enterNicknameText,
            animatedTextColor = animatedTextColor,
            focusManager = focusManager,
            focusRequester = focusRequester,
            nickname = nickname,
            emojiIndex = emojiIndex,
            onEmojiChange = onEmojiChange,
            selectedEmoji = selectedEmoji
        )
    }
}

@Composable
private fun UserSectionBody(
    editableNickname: MutableState<String>,
    onNicknameChange: (String) -> Unit,
    isEditing: MutableState<Boolean>,
    enterNicknameText: String,
    animatedTextColor: Float,
    focusManager: FocusManager,
    focusRequester: FocusRequester,
    nickname: String,
    emojiIndex: MutableIntState,
    onEmojiChange: (String) -> Unit,
    selectedEmoji: String,
) {
    Box(
        modifier = Modifier
            .layoutId("box")
            .fillMaxWidth()
            .height(nicknameInputHeight)
            .padding(paddingSmall)
            .background(
                color = MaterialTheme.colorScheme.onSurface,
                shape = RoundedCornerShape(paddingNormal)
            ),
        contentAlignment = Alignment.TopCenter
    ) {

        NicknameBanner(
            editableNickname = editableNickname,
            onNicknameChange = onNicknameChange,
            isEditing = isEditing,
            enterNicknameText = enterNicknameText,
            animatedTextColor = animatedTextColor,
            focusManager = focusManager,
            focusRequester = focusRequester,
            nickname = nickname
        )

        EmojiIcon(
            modifier = Modifier
                .layoutId("emoji")
                .align(Alignment.TopCenter),
            emojiIndex = emojiIndex,
            onEmojiChange = onEmojiChange,
            selectedEmoji = selectedEmoji
        )
    }
}

@Composable
private fun NicknameBanner(
    editableNickname: MutableState<String>,
    onNicknameChange: (String) -> Unit,
    isEditing: MutableState<Boolean>,
    enterNicknameText: String,
    animatedTextColor: Float,
    focusManager: FocusManager,
    focusRequester: FocusRequester,
    nickname: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .height(nicknameInputHeight)
            .padding(top = paddingSmall)
            .padding(horizontal = paddingSmall)
    ) {

        EditIcon(
            modifier = Modifier
                .size(iconExtraLarge)
                .align(Alignment.End),
            editableNickname = editableNickname,
            onNicknameChange = onNicknameChange,
            isEditing = isEditing
        )

        Spacer(modifier = Modifier.height(paddingExtraSmall))

        if (isEditing.value) {
            NicknameInput(
                editableNickname = editableNickname,
                enterNicknameText = enterNicknameText,
                isEditing = isEditing,
                animatedTextColor = animatedTextColor,
                onNicknameChange = onNicknameChange,
                focusManager = focusManager,
                focusRequester = focusRequester
            )
        } else {
            Text(
                text = nickname.ifEmpty { stringResource(id = R.string.nickname) },
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.tertiary.copy(alpha = animatedTextColor)
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun EditIcon(
    modifier: Modifier,
    editableNickname: MutableState<String>,
    onNicknameChange: (String) -> Unit,
    isEditing: MutableState<Boolean>,
) {
    IconButton(
        onClick = {
            if (editableNickname.value.isEmpty()) {
                editableNickname.value = ""
            } else {
                onNicknameChange(editableNickname.value)
            }
            isEditing.value = !isEditing.value
        },
        modifier = modifier
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(
                id =
                if (isEditing.value) R.drawable.ic_close
                else R.drawable.ic_nickname_edit
            ),
            tint = MaterialTheme.colorScheme.secondary,
            contentDescription = null
        )
    }
}

@Composable
private fun NicknameInput(
    editableNickname: MutableState<String>,
    enterNicknameText: String,
    isEditing: MutableState<Boolean>,
    animatedTextColor: Float,
    onNicknameChange: (String) -> Unit,
    focusManager: FocusManager,
    focusRequester: FocusRequester,
) {
    BasicTextField(
        value = editableNickname.value,
        onValueChange = { newValue ->
            if (newValue.all { it.isLetter() || it.isWhitespace() }) {
                editableNickname.value = newValue
            }
        },
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.tertiary),
        decorationBox = { innerTextField ->
            if (editableNickname.value.isEmpty()) {
                NicknameText(
                    enterNicknameText = enterNicknameText,
                    isEditing = isEditing,
                    editableNickname = editableNickname,
                    animatedTextColor = animatedTextColor
                )
            }
            innerTextField()
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                onNicknameChange(editableNickname.value)
                isEditing.value = false
                focusManager.clearFocus()
            }
        ),
        modifier = Modifier
            .padding(horizontal = paddingMedium)
            .fillMaxWidth()
            .focusRequester(focusRequester)
    )
}

@Composable
private fun NicknameText(
    enterNicknameText: String,
    isEditing: MutableState<Boolean>,
    editableNickname: MutableState<String>,
    animatedTextColor: Float,
) {
    if (isEditing.value || editableNickname.value.isEmpty()) {
        Text(
            text = enterNicknameText,
            color = MaterialTheme.colorScheme.tertiary.copy(alpha = animatedTextColor),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun EmojiIcon(
    modifier: Modifier,
    emojiIndex: MutableIntState,
    onEmojiChange: (String) -> Unit,
    selectedEmoji: String,
) {
    Box(
        modifier = modifier
            .offset(y = (-44).dp)
            .zIndex(1f)
    ) {
        Button(
            onClick = {
                emojiIndex.intValue = (emojiIndex.intValue + 1) % Constants.emojis.size
                onEmojiChange(Constants.emojis[emojiIndex.intValue])
            },
            modifier = Modifier.align(Alignment.TopCenter),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Unspecified,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Color.Transparent
            )
        ) {
            Text(
                text = selectedEmoji,
                fontSize = 48.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun NicknameInputPreview() {
    TempusTheme {
        UserSection(
            nickname = "",
            onNicknameChange = {},
            selectedEmoji = "\uD83D\uDE36",
            onEmojiChange = {}
        )
    }
}