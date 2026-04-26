package com.example.deku.feature.profile.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.deku.R
import com.example.deku.core.network.RetrofitProvider
import com.example.deku.databinding.FragmentProfileBinding
import com.example.deku.feature.profile.data.ProfileRepository
import com.example.deku.feature.profile.model.ProfileUser
import com.example.deku.feature.profile.presentation.ProfileUiState
import com.example.deku.feature.profile.presentation.ProfileViewModel
import com.example.deku.feature.profile.presentation.ProfileViewModelFactory

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory(
            repository = ProfileRepository(RetrofitProvider.profileService),
        )
    }
    private lateinit var followingAdapter: FollowingAdapter
    private var lastErrorMessage: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        setupFollowingList()
        setupActions()
        observeUiState()

        if (profileViewModel.uiState.value == ProfileUiState.Idle) {
            profileViewModel.loadProfileScreen()
        }
    }

    private fun setupFollowingList() {
        followingAdapter = FollowingAdapter()
        binding.rvFollowing.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false,
        )
        binding.rvFollowing.adapter = followingAdapter
    }

    private fun setupActions() {
        binding.btnRetry.setOnClickListener {
            lastErrorMessage = null
            profileViewModel.loadProfileScreen()
        }
    }

    private fun observeUiState() {
        profileViewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                ProfileUiState.Idle -> Unit
                ProfileUiState.Loading -> renderLoading()
                is ProfileUiState.Success -> renderSuccess(state)
                is ProfileUiState.Error -> renderError(state.message)
            }
        }
    }

    private fun renderLoading() {
        lastErrorMessage = null
        binding.progressBar.isVisible = true
        binding.cardProfile.isVisible = false
        binding.tvFollowingTitle.isVisible = false
        binding.tvFollowingSubtitle.isVisible = false
        binding.rvFollowing.isVisible = false
        binding.tvFollowingEmpty.isVisible = false
        binding.layoutError.isVisible = false
    }

    private fun renderSuccess(state: ProfileUiState.Success) {
        lastErrorMessage = null
        binding.progressBar.isVisible = false
        binding.layoutError.isVisible = false
        binding.cardProfile.isVisible = true
        binding.tvFollowingTitle.isVisible = true
        binding.tvFollowingSubtitle.isVisible = true
        binding.rvFollowing.isVisible = true

        bindProfile(state.user)
        followingAdapter.updateItems(state.followingUsers)
        binding.tvFollowingTitle.text = getString(
            R.string.profile_following_title_with_count,
            state.followingUsers.size,
        )
        binding.tvFollowingEmpty.isVisible = state.followingUsers.isEmpty()
    }

    private fun renderError(message: String) {
        binding.progressBar.isVisible = false
        binding.cardProfile.isVisible = false
        binding.tvFollowingTitle.isVisible = false
        binding.tvFollowingSubtitle.isVisible = false
        binding.rvFollowing.isVisible = false
        binding.tvFollowingEmpty.isVisible = false
        binding.layoutError.isVisible = true
        binding.tvErrorMessage.text = message

        if (lastErrorMessage != message) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            lastErrorMessage = message
        }
    }

    private fun bindProfile(user: ProfileUser) {
        binding.ivProfile.load(user.avatarUrl) {
            crossfade(true)
            placeholder(R.drawable.nike_logo)
            error(R.drawable.nike_logo)
        }
        binding.tvProfileName.text = user.name
        binding.tvProfileEmail.text = user.email
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
