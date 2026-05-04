package com.example.umc10th

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.umc10th.databinding.FragmentProfileBinding
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var profileImageView: ImageView
    private lateinit var profileProgressBar: ProgressBar
    private val followingProfileAdapter = FollowingProfileAdapter()
    private var lastShownErrorMessage: String? = null

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
        setupProfileImageView()
        setupFollowingRecyclerView()
        observeProfile()
        viewModel.loadUserProfile()
    }

    private fun setupProfileImageView() {
        profileImageView = ImageView(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        profileProgressBar = ProgressBar(requireContext()).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER
            )
        }
        binding.userProfile.addView(profileImageView)
        binding.userProfile.addView(profileProgressBar)
    }

    private fun setupFollowingRecyclerView() {
        binding.rvFollowing.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.rvFollowing.adapter = followingProfileAdapter
    }

    private fun observeProfile() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    profileProgressBar.visibility = if (uiState.isLoading) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
                    profileImageView.visibility = if (uiState.profileBitmap == null) {
                        View.INVISIBLE
                    } else {
                        View.VISIBLE
                    }
                    if (uiState.isLoading) {
                        binding.userName.text = ""
                    }
                    if (uiState.userName.isNotEmpty()) {
                        binding.userName.text = uiState.userName
                    }
                    uiState.profileBitmap?.let { profileImageView.setImageBitmap(it) }
                    followingProfileAdapter.submitList(uiState.followingProfiles)
                    showErrorToast(uiState.errorMessage)
                }
            }
        }
    }

    private fun showErrorToast(errorMessage: String?) {
        if (errorMessage.isNullOrBlank()) return
        if (lastShownErrorMessage == errorMessage) return

        lastShownErrorMessage = errorMessage
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        binding.rvFollowing.adapter = null
        super.onDestroyView()
        _binding = null
    }
}
