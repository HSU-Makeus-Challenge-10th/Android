package com.example.deku.feature.profile

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.deku.R
import com.example.deku.core.designsystem.ColorDivider
import com.example.deku.core.designsystem.ColorFrameBackground
import com.example.deku.core.designsystem.ColorTextPrimary
import com.example.deku.core.designsystem.ColorTextSecondary
import com.example.deku.core.designsystem.theme.DekuTheme
import com.example.deku.feature.profile.model.ProfileUser
import com.example.deku.feature.profile.presentation.ProfileUiState
import com.example.deku.feature.profile.presentation.ProfileViewModel
import com.example.deku.feature.profile.presentation.ProfileViewModelFactory

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel(factory = ProfileViewModelFactory()),
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val errorMessage = (uiState as? ProfileUiState.Error)?.message

    LaunchedEffect(Unit) {
        viewModel.loadProfileScreen()
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    when (val state = uiState) {
        ProfileUiState.Idle,
        ProfileUiState.Loading -> ProfileLoadingScreen(modifier = modifier)

        is ProfileUiState.Success -> ProfileContent(
            user = state.user,
            followingUsers = state.followingUsers,
            modifier = modifier
        )

        is ProfileUiState.Error -> ProfileErrorScreen(
            message = state.message,
            onRetryClick = { viewModel.loadProfileScreen(forceRefresh = true) },
            modifier = modifier
        )
    }
}

@Composable
private fun ProfileLoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 88.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        CircularProgressIndicator(color = ColorTextPrimary)
    }
}

@Composable
private fun ProfileErrorScreen(
    message: String,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
            .padding(top = 88.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.profile_error_title),
            color = ColorTextPrimary,
            fontSize = 20.sp,
            lineHeight = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = message,
            color = ColorTextSecondary,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = onRetryClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text(
                text = stringResource(id = R.string.profile_retry),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun ProfileContent(
    user: ProfileUser,
    followingUsers: List<ProfileUser>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentPadding = PaddingValues(
            start = 24.dp,
            top = 64.dp,
            end = 24.dp,
            bottom = 32.dp
        )
    ) {
        item(contentType = "profileCard") {
            ProfileCard(user = user)
        }

        item(contentType = "followingHeader") {
            Spacer(modifier = Modifier.height(28.dp))
            FollowingHeader(count = followingUsers.size)
        }

        item(contentType = "followingPager") {
            Spacer(modifier = Modifier.height(18.dp))
            if (followingUsers.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.profile_following_empty),
                    color = ColorTextSecondary,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            } else {
                FollowingPager(users = followingUsers)
            }
        }
    }
}

@Composable
private fun ProfileCard(user: ProfileUser) {
    Column(modifier = Modifier.fillMaxWidth()) {
        ProfileAvatar(
            avatarUrl = user.avatarUrl,
            contentDescription = stringResource(id = R.string.profile_avatar),
            modifier = Modifier
                .size(92.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = user.name,
            color = ColorTextPrimary,
            fontSize = 24.sp,
            lineHeight = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(
            onClick = {},
            modifier = Modifier
                .height(46.dp)
                .align(Alignment.CenterHorizontally),
            contentPadding = PaddingValues(horizontal = 20.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White,
                contentColor = ColorTextPrimary
            ),
            border = androidx.compose.foundation.BorderStroke(1.dp, ColorDivider),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text(
                text = stringResource(id = R.string.profile_edit),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(28.dp))
        ProfileMenuRow()
        Spacer(modifier = Modifier.height(22.dp))
        HorizontalDivider(color = ColorDivider, thickness = 1.dp)
        Spacer(modifier = Modifier.height(18.dp))
        BenefitsRow()
        Spacer(modifier = Modifier.height(18.dp))
        HorizontalDivider(color = ColorDivider, thickness = 1.dp)
    }
}

@Composable
private fun ProfileAvatar(
    avatarUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(avatarUrl)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        placeholder = painterResource(id = R.drawable.nike_logo),
        error = painterResource(id = R.drawable.nike_logo),
        fallback = painterResource(id = R.drawable.nike_logo),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(CircleShape)
            .background(ColorFrameBackground)
    )
}

@Composable
private fun ProfileMenuRow() {
    val menuItems = listOf(
        ProfileMenuItem(R.string.profile_menu_order, R.drawable.ic_profile_order),
        ProfileMenuItem(R.string.profile_menu_pass, R.drawable.ic_profile_pass),
        ProfileMenuItem(R.string.profile_menu_event, R.drawable.ic_profile_event),
        ProfileMenuItem(R.string.profile_menu_settings, R.drawable.ic_profile_settings)
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        menuItems.forEach { item ->
            ProfileMenuButton(
                item = item,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ProfileMenuButton(
    item: ProfileMenuItem,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val label = stringResource(id = item.labelResId)
        Icon(
            painter = painterResource(id = item.iconResId),
            contentDescription = label,
            tint = ColorTextPrimary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            color = ColorTextPrimary,
            fontSize = 14.sp,
            lineHeight = 18.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun BenefitsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(id = R.string.profile_benefits_title),
                color = ColorTextPrimary,
                fontSize = 18.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(id = R.string.profile_benefits_subtitle),
                color = ColorTextSecondary,
                fontSize = 13.sp,
                lineHeight = 18.sp
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_right),
            contentDescription = null,
            tint = ColorTextSecondary,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun FollowingHeader(count: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.profile_following_title_with_count, count),
            color = ColorTextPrimary,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = stringResource(id = R.string.profile_following_edit),
            color = ColorTextPrimary,
            fontSize = 15.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun FollowingPager(users: List<ProfileUser>) {
    val pagerState = rememberPagerState(pageCount = { users.size })

    HorizontalPager(
        state = pagerState,
        pageSize = PageSize.Fixed(100.dp),
        pageSpacing = 12.dp,
        contentPadding = PaddingValues(end = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(124.dp)
    ) { page ->
        FollowingUserCard(user = users[page])
    }
}

@Composable
private fun FollowingUserCard(user: ProfileUser) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileAvatar(
            avatarUrl = user.avatarUrl,
            contentDescription = stringResource(id = R.string.profile_following_avatar),
            modifier = Modifier.size(72.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = user.name,
            color = ColorTextPrimary,
            fontSize = 13.sp,
            lineHeight = 16.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

private data class ProfileMenuItem(
    val labelResId: Int,
    @param:DrawableRes val iconResId: Int,
)

@Preview(showBackground = true)
@Composable
private fun ProfileContentPreview() {
    DekuTheme {
        ProfileContent(
            user = ProfileUser(
                id = 1,
                name = "George Bluth",
                email = "george.bluth@reqres.in",
                avatarUrl = ""
            ),
            followingUsers = listOf(
                ProfileUser(2, "Janet Weaver", "janet.weaver@reqres.in", ""),
                ProfileUser(3, "Emma Wong", "emma.wong@reqres.in", ""),
                ProfileUser(4, "Eve Holt", "eve.holt@reqres.in", "")
            )
        )
    }
}
