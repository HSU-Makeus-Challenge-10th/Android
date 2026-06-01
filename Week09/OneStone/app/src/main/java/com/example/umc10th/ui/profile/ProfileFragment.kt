package com.example.umc10th.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    /*
    Legacy XML implementation was intentionally disabled for the Compose migration.

    The previous implementation used:
    - FragmentProfileBinding
    - ImageView / ProgressBar
    - RecyclerView + FollowingProfileAdapter
    - Bitmap loading state from ProfileViewModel

    Profile is now rendered by ProfileScreen with:
    - ProfileViewModel URL based state
    - Coil AsyncImage
    - HorizontalPager for following profiles
    */

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return View(requireContext())
    }
}
