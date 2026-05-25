package com.example.week09.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.week09.R
import com.example.week09.data.model.UserData
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPageScreen(
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    var showEditSheet by remember { mutableStateOf(false) }
    var selectedFollowingUser by remember { mutableStateOf<UserData?>(null) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    val followingList = uiState.followingList

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                ) {
                    ProfileHeaderSection(
                        profile = uiState.myProfile,
                        onEditClick = { showEditSheet = true },
                    )

                    ProfileQuickMenuRow()

                    SectionDivider()

                    NikeMemberBenefitRow()

                    SectionDivider()

                    FollowingSectionHeader(count = followingList.size)

                    if (followingList.isEmpty()) {
                        Text(
                            text = uiState.errorMessage ?: "팔로잉 목록이 없습니다.",
                            fontSize = 14.sp,
                            color = Color(0xFF888888),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 24.dp),
                            textAlign = TextAlign.Center,
                        )
                    } else {
                        FollowingPagerSection(
                            followingList = followingList,
                            onUserClick = { selectedFollowingUser = it },
                        )
                    }
                }

                Text(
                    text = "회원 가입일: 2025년 9월",
                    fontSize = 12.sp,
                    color = Color(0xFF888888),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF5F5F5))
                        .padding(vertical = 12.dp),
                )
            }
        }
    }

    if (showEditSheet) {
        ModalBottomSheet(
            onDismissRequest = { showEditSheet = false },
            sheetState = sheetState,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "프로필 수정",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "이번 주차에서는 ModalBottomSheet 연습용 UI입니다.",
                    fontSize = 14.sp,
                    color = Color(0xFF888888),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(24.dp))
                TextButton(
                    onClick = {
                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showEditSheet = false
                            }
                        }
                    },
                ) {
                    Text("닫기")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    selectedFollowingUser?.let { user ->
        FollowingImageDialog(
            user = user,
            onDismiss = { selectedFollowingUser = null },
        )
    }
}

@Composable
private fun ProfileHeaderSection(
    profile: UserData?,
    onEditClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = profile?.avatar,
            contentDescription = profile?.displayName ?: "프로필 이미지",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color(0xFFD9D9D9)),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = profile?.displayName ?: "",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = profile?.email ?: "",
            fontSize = 14.sp,
            color = Color(0xFF888888),
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(
            onClick = onEditClick,
            shape = RoundedCornerShape(50),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 10.dp),
        ) {
            Text(
                text = "프로필 수정",
                fontSize = 14.sp,
                color = Color.Black,
            )
        }
    }
}

@Composable
private fun ProfileQuickMenuRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ProfileMenuItem(R.drawable.ic_order, "주문", Modifier.weight(1f))
        VerticalDivider()
        ProfileMenuItem(R.drawable.ic_pass, "패스", Modifier.weight(1f))
        VerticalDivider()
        ProfileMenuItem(R.drawable.ic_event, "이벤트", Modifier.weight(1f))
        VerticalDivider()
        ProfileMenuItem(R.drawable.ic_settings, "설정", Modifier.weight(1f))
    }
}

@Composable
private fun NikeMemberBenefitRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "나이키 멤버 혜택",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "0개 사용 가능",
                fontSize = 13.sp,
                color = Color(0xFF888888),
            )
        }
        Icon(
            painter = painterResource(R.drawable.ic_chevron_right),
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(24.dp),
        )
    }
}

@Composable
private fun FollowingSectionHeader(count: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(start = 20.dp, end = 20.dp, top = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = if (count > 0) "팔로잉 $count" else "",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = "편집",
            fontSize = 14.sp,
            color = Color(0xFF888888),
        )
    }
}

@Composable
private fun FollowingPagerSection(
    followingList: List<UserData>,
    onUserClick: (UserData) -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { followingList.size })

    LaunchedEffect(followingList.size) {
        if (pagerState.currentPage >= followingList.size) {
            pagerState.scrollToPage(0)
        }
    }

    FollowingHorizontalPager(
        followingList = followingList,
        pagerState = pagerState,
        onUserClick = onUserClick,
    )

    PagerDotIndicator(
        pagerState = pagerState,
        pageCount = followingList.size,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
    )
}

@Composable
private fun FollowingHorizontalPager(
    followingList: List<UserData>,
    pagerState: PagerState,
    onUserClick: (UserData) -> Unit,
) {
    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 48.dp),
        pageSpacing = 16.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
    ) { page ->
        val user = followingList[page]
        FollowingUserPage(
            user = user,
            onClick = { onUserClick(user) },
        )
    }
}

@Composable
private fun FollowingUserPage(
    user: UserData,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = user.avatar,
            contentDescription = user.displayName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(Color(0xFFD9D9D9)),
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = user.displayName,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "탭하여 크게 보기",
            fontSize = 12.sp,
            color = Color(0xFF888888),
        )
    }
}

@Composable
private fun PagerDotIndicator(
    pagerState: PagerState,
    pageCount: Int,
    modifier: Modifier = Modifier,
) {
    if (pageCount <= 1) return

    var currentPage by remember { mutableStateOf(pagerState.currentPage) }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .distinctUntilChanged()
            .collect { currentPage = it }
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(pageCount) { index ->
            val isSelected = currentPage == index
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(if (isSelected) 10.dp else 8.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) Color.Black else Color(0xFFCCCCCC)),
            )
        }
    }
}

@Composable
private fun FollowingImageDialog(
    user: UserData,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = user.displayName)
        },
        text = {
            AsyncImage(
                model = user.avatar,
                contentDescription = user.displayName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF0F0F0)),
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("닫기")
            }
        },
    )
}

@Composable
private fun ProfileMenuItem(
    iconRes: Int,
    label: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = label,
            tint = Color.Unspecified,
            modifier = Modifier.size(28.dp),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333),
        )
    }
}

@Composable
private fun VerticalDivider() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(32.dp)
            .background(Color(0xFFEEEEEE)),
    )
}

@Composable
private fun SectionDivider() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .background(Color(0xFFF5F5F5)),
    )
}
