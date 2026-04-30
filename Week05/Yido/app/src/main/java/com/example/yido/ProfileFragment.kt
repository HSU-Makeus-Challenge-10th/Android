package com.example.yido

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.yido.databinding.FragmentProfileBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var userAdapter: UserAdapter

    // 내 프로필 유저 ID (1번 유저를 "나"로 사용)
    private val myUserId = 1

    companion object {
        private const val TAG = "ProfileFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        fetchMyProfile(myUserId)
        fetchFollowingList()
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter()
        binding.rvFollowing.adapter = userAdapter
    }

    private fun fetchMyProfile(userId: Int) {
        // 코루틴 시작 (lifecycleScope)
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                // 비동기 통신 (IO 스레드)
                val response = withContext(Dispatchers.IO) {
                    ApiClient.reqResService.getUserProfile(userId)
                }

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        val user = body.data
                        updateProfileUI(user)
                    }
                } else {
                    Log.e(TAG, "API 호출 실패: ${response.code()}")
                    Toast.makeText(requireContext(), "내 정보를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e(TAG, "통신 오류: ${e.message}")
                Toast.makeText(requireContext(), "통신 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchFollowingList() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    ApiClient.reqResService.getUserList(1)
                }

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        // 자기 자신(myUserId)을 제외한 목록만 표시
                        val filtered = body.data.filter { it.id != myUserId }
                        userAdapter.submitList(filtered)

                        // "팔로잉 N" 동적 표시
                        binding.tvFollowingTitle.text =
                            getString(R.string.profile_following_list, filtered.size)
                    }
                } else {
                    Log.e(TAG, "API 호출 실패: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "통신 오류: ${e.message}")
            }
        }
    }

    private fun updateProfileUI(user: UserData) {
        val fullName = "${user.firstName} ${user.lastName}"
        binding.tvMyName.text = fullName
        binding.tvMyEmail.text = user.email

        Glide.with(this)
            .load(user.avatar)
            .circleCrop()
            .into(binding.ivMyAvatar)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
