package com.example.umc10th.ui.profile

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.umc10th.R

@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val selectedFollowing = remember { mutableStateOf<FollowingProfileUiState?>(null) }

    LaunchedEffect(Unit) {
        viewModel.loadUserProfile()
    }

    LaunchedEffect(uiState.errorMessage) {
        val errorMessage = uiState.errorMessage
        if (!errorMessage.isNullOrBlank()) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(21.dp))
        ProfileHeader(uiState = uiState)
        ProfileShortcutRow()
        SectionDivider()
        BenefitSection()
        SectionDivider()
        FollowingSection(
            followingProfiles = uiState.followingProfiles,
            onFollowingClick = { profile ->
                selectedFollowing.value = profile
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        JoinedDateFooter()
    }

    selectedFollowing.value?.let { profile ->
        FollowingImageDialog(
            profile = profile,
            onDismiss = { selectedFollowing.value = null }
        )
    }
}

@Composable
private fun ProfileHeader(uiState: ProfileUiState) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(84.dp)
                .clip(CircleShape)
                .background(Color(0xFFD9D9D9)),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = uiState.profileImageUrl,
                contentDescription = "Profile image",
                placeholder = painterResource(id = R.drawable.ic_user),
                error = painterResource(id = R.drawable.ic_user),
                fallback = painterResource(id = R.drawable.ic_user),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    color = Color.Black,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Text(
            text = uiState.userName,
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 30.dp)
        )

        Button(
            onClick = {},
            modifier = Modifier
                .padding(top = 30.dp)
                .size(width = 180.dp, height = 51.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            border = BorderStroke(width = 1.dp, color = Color(0xFFE0E0E0))
        ) {
            Text(text = "프로필 수정", fontSize = 16.sp)
        }
    }
}

@Composable
private fun ProfileShortcutRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 42.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfileShortcutItem(
            iconResId = R.drawable.ic_order,
            label = "주문",
            modifier = Modifier.weight(1f)
        )
        VerticalShortcutDivider()
        ProfileShortcutItem(
            iconResId = R.drawable.ic_pass,
            label = "패스",
            modifier = Modifier.weight(1f)
        )
        VerticalShortcutDivider()
        ProfileShortcutItem(
            iconResId = R.drawable.ic_calendar,
            label = "이벤트",
            modifier = Modifier.weight(1f)
        )
        VerticalShortcutDivider()
        ProfileShortcutItem(
            iconResId = R.drawable.ic_setting,
            label = "설정",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ProfileShortcutItem(
    iconResId: Int,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = label,
            tint = Color.Unspecified,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            color = Color.Black,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}

@Composable
private fun VerticalShortcutDivider() {
    Box(
        modifier = Modifier
            .size(width = 1.dp, height = 30.dp)
            .background(Color(0xFFE0E0E0))
    )
}

@Composable
private fun BenefitSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "나이키 멤버 혜택",
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "0개 사용 가능",
                color = Color(0xFF767676),
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 6.dp)
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_closed),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(14.dp)
        )
    }
}

@Composable
private fun FollowingSection(
    followingProfiles: List<FollowingProfileUiState>,
    onFollowingClick: (FollowingProfileUiState) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { followingProfiles.size.coerceAtLeast(1) })

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 28.dp, bottom = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "팔로잉 (${followingProfiles.size})",
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "편집",
                color = Color(0xFF767676),
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (followingProfiles.isEmpty()) {
            EmptyFollowingContent()
        } else {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            ) { page ->
                FollowingProfilePage(
                    profile = followingProfiles[page],
                    onClick = { onFollowingClick(followingProfiles[page]) }
                )
            }
            PagerDotIndicator(
                pagerState = pagerState,
                pageCount = followingProfiles.size,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
private fun FollowingProfilePage(
    profile: FollowingProfileUiState,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = profile.avatarUrl,
            contentDescription = profile.name,
            placeholder = painterResource(id = R.drawable.ic_user),
            error = painterResource(id = R.drawable.ic_user),
            fallback = painterResource(id = R.drawable.ic_user),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(84.dp)
                .clip(CircleShape)
                .background(Color(0xFFD9D9D9))
        )
        Text(
            text = profile.name,
            color = Color.Black,
            fontSize = 13.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
private fun FollowingImageDialog(
    profile: FollowingProfileUiState,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = profile.name,
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        text = {
            ZoomableImage(
                imageUrl = profile.avatarUrl,
                contentDescription = profile.name
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "닫기", color = Color.Black)
            }
        },
        containerColor = Color.White
    )
}

@Composable
private fun ZoomableImage(
    imageUrl: String,
    contentDescription: String?
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF2F2F2))
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            placeholder = painterResource(id = R.drawable.ic_user),
            error = painterResource(id = R.drawable.ic_user),
            fallback = painterResource(id = R.drawable.ic_user),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        val nextScale = (scale * zoom).coerceIn(1f, 4f)
                        scale = nextScale
                        offset = if (nextScale > 1f) {
                            offset + pan
                        } else {
                            Offset.Zero
                        }
                    }
                }
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = offset.x
                    translationY = offset.y
                }
        )
    }
}

@Composable
private fun PagerDotIndicator(
    pagerState: PagerState,
    pageCount: Int,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(top = 8.dp)
    ) {
        repeat(pageCount) { index ->
            val isSelected = pagerState.currentPage == index
            Box(
                modifier = Modifier
                    .size(if (isSelected) 8.dp else 6.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) Color.Black else Color(0xFFCFCFCF))
            )
        }
    }
}

@Composable
private fun EmptyFollowingContent() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(108.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "팔로잉 목록이 없습니다.",
            color = Color(0xFF767676),
            fontSize = 13.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun SectionDivider() {
    Spacer(modifier = Modifier.height(24.dp))
    HorizontalDivider(color = Color(0xFFF6F6F6), thickness = 7.5.dp)
}

@Composable
private fun JoinedDateFooter() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(47.dp)
            .background(Color(0xFFF6F6F6)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "회원 가입일: 2026년 4월",
            color = Color(0xFF767676),
            fontSize = 12.sp
        )
    }
}
