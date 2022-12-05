package com.mobileapp.deafassist

import android.graphics.Color
import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobileapp.deafassist.databinding.FragmentAboutBinding
import com.mobileapp.deafassist.databinding.FragmentHomeBinding

class AboutFragment : Fragment() {
    private var _binding: FragmentAboutBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAboutBinding.inflate(inflater, container, false)

        // Setup Hyperlink on the text
        binding.aboutTextView.movementMethod = LinkMovementMethod.getInstance();
        return binding.root
    }
}
