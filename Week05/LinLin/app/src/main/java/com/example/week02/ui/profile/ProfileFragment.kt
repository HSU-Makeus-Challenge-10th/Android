package com.example.week02.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.week02.R
import com.example.week02.data.remote.reqres.ReqresRepository
import com.example.week02.databinding.FragmentProfileBinding
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val repository = ReqresRepository()
    private val followingAdapter = FollowingAvatarAdapter()
    private val TAG = "ProfileFragment"

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
        binding.rvFollowing.adapter = followingAdapter
        binding.rvFollowing.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // ReqRes: userId = 1
        viewLifecycleOwner.lifecycleScope.launch {
            runCatching { repository.getUser(1) }
                .onSuccess { user ->
                    binding.tvNickname.text = user.displayName
                    binding.ivProfileAvatar.load(user.avatar)
                }
                .onFailure { e ->
                    Log.e(TAG, "getUser failed", e)
                }
        }

        // Following list (RecyclerView)
        viewLifecycleOwner.lifecycleScope.launch {
            runCatching { repository.getFollowingUsers(page = 2) }
                .onSuccess { users ->
                    followingAdapter.submitList(users.take(8))
                }
                .onFailure { e ->
                    Log.e(TAG, "listUsers failed", e)
                }
        }

        // 프로필 수정 버튼 클릭 시
        binding.btnEditProfile.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_container, ProfileEditFragment())
                // 뒤로가기 가능하게 설정
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
