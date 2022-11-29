package com.mobileapp.deafassist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.mobileapp.deafassist.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val model: DeafAssistViewModel by activityViewModels()

        // Create the observer which updates the UI.
        val textObserver = Observer<String> { newText ->
            // Update the UI, in this case, a TextView.
            binding.textView.text = newText
        }
        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        model.currentText.observe(viewLifecycleOwner, textObserver)

        // Listener function to update the ViewModel's `currentText` from `editTextInput`
        binding.editTextInput.doOnTextChanged { text, start, before, count ->
            model.currentText.value = text.toString()
        }

        return binding.root
    }
}
