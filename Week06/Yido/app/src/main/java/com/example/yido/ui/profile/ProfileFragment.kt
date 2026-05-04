package com.example.yido.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.yido.R
import com.example.yido.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userAdapter = UserAdapter()
        binding.rvFollowing.adapter = userAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                state.myProfile?.let { user ->
                    val fullName = "${user.firstName} ${user.lastName}"
                    binding.tvMyName.text = fullName
                    binding.tvMyEmail.text = user.email
                    Glide.with(this@ProfileFragment)
                        .load(user.avatar)
                        .circleCrop()
                        .into(binding.ivMyAvatar)
                }

                if (state.followingList.isNotEmpty()) {
                    userAdapter.submitList(state.followingList)
                    binding.tvFollowingTitle.text =
                        getString(R.string.profile_following_list, state.followingList.size)
                }

                state.errorMessage?.let { message ->
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
