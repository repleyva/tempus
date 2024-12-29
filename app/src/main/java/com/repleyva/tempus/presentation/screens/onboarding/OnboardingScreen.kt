package com.repleyva.tempus.presentation.screens.onboarding

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.repleyva.tempus.presentation.common.GenericButton
import com.repleyva.tempus.presentation.common.GenericOutlinedButton
import com.repleyva.tempus.presentation.theme.Dimensions.cornerMedium
import com.repleyva.tempus.presentation.theme.Dimensions.onBoardingDescription
import com.repleyva.tempus.presentation.theme.Dimensions.paddingLarge
import com.repleyva.tempus.presentation.theme.TempusTheme
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    event: (OnboardingEvent) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .testTag("OnboardingScreen")
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        val pages = getPages()

        val pageState = rememberPagerState(initialPage = 0) { pages.size }

        val buttonsState = remember {
            derivedStateOf {
                when (pageState.currentPage) {
                    0 -> listOf("", "Explore")
                    1 -> listOf("Back", "Next")
                    2 -> listOf("Back", "Get Started")
                    else -> listOf("", "")
                }
            }
        }

        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {

            OnboardingBackgroud(
                pageState = pageState,
                pages = pages
            )

            OnboardingBody(
                modifier = Modifier
                    .fillMaxHeight(0.45f)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                pages = pages,
                pageState = pageState,
                buttonsState = buttonsState,
                event = event
            )
        }
    }
}

@Composable
private fun OnboardingBody(
    modifier: Modifier,
    pages: List<Page>,
    pageState: PagerState,
    buttonsState: State<List<String>>,
    event: (OnboardingEvent) -> Unit,
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = modifier,
        shape = RoundedCornerShape(topStart = cornerMedium, topEnd = cornerMedium),
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingLarge)
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            OnboardingDescription(
                pages = pages,
                pageState = pageState
            )

            OnboardingActions(
                buttonsState = buttonsState,
                pageState = pageState,
                event = event,
                pages = pages
            )
        }
    }
}

@Composable
private fun OnboardingBackgroud(
    pageState: PagerState,
    pages: List<Page>,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White
    ) {
        HorizontalPager(
            state = pageState,
            modifier = Modifier.fillMaxSize(),
        ) { index ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                OnboardingPageImage(page = pages[index])
            }
        }
    }
}

@Composable
private fun OnboardingDescription(
    pages: List<Page>,
    pageState: PagerState,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(onBoardingDescription)
    ) {
        OnboardingPageText(page = pages[pageState.currentPage])
    }
}

@Composable
private fun OnboardingActions(
    buttonsState: State<List<String>>,
    pageState: PagerState,
    event: (OnboardingEvent) -> Unit,
    pages: List<Page>,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        OnboardingButtons(
            buttonsState = buttonsState,
            pageState = pageState,
            event = event
        )

        OnboardingPageIndicator(
            modifier = Modifier
                .width(100.dp)
                .alpha(0.8f),
            pagesSize = pages.size,
            selectedPage = pageState.currentPage
        )
    }
}

@Composable
private fun OnboardingButtons(
    buttonsState: State<List<String>>,
    pageState: PagerState,
    event: (OnboardingEvent) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(modifier = Modifier.weight(0.25f))

        val scope = rememberCoroutineScope()

        if (buttonsState.value[0].isNotEmpty()) {
            GenericOutlinedButton(
                text = buttonsState.value[0],
                onClick = {
                    scope.launch {
                        pageState.animateScrollToPage(
                            page = pageState.currentPage - 1
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.weight(0.25f))
        }

        GenericButton(
            text = buttonsState.value[1],
            onClick = {
                scope.launch {
                    if (pageState.currentPage == 2) {
                        event(OnboardingEvent.SaveAppEntry)
                    } else {
                        pageState.animateScrollToPage(
                            page = pageState.currentPage + 1
                        )
                    }
                }
            }
        )

        Spacer(modifier = Modifier.weight(0.25f))
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun OnboardingScreenPreview() {
    TempusTheme(
        dynamicColor = false
    ) {
        OnboardingScreen()
    }
}