package com.mobileapp.deafassist

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mobileapp.deafassist.databinding.FragmentItemListDialogListDialogItemBinding
import com.mobileapp.deafassist.databinding.FragmentItemListDialogListDialogBinding

// TODO: Customize parameter argument names
const val ARG_ITEM_COUNT = "item_count"

/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    FavoriteListDialogFragment.show(supportFragmentManager, "dialog")
 * </pre>
 */
class FavoriteListDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentItemListDialogListDialogBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemListDialogListDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = FavoriteAdapter(
            listOf(
                FavoriteViewModel("Sepehr"),
                FavoriteViewModel("Smart"),
                FavoriteViewModel("Student")
            )
        )
    }

    /**
     * Provide a reference to the views we use (custom ViewHolder).
     */
    private inner class ViewHolder internal constructor(binding: FragmentItemListDialogListDialogItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        internal val text: TextView = binding.text
    }

    // Adapter Class for RecyclerView
    private inner class FavoriteAdapter internal constructor(private val mList: List<FavoriteViewModel>) :
        RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                FragmentItemListDialogListDialogItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }

        // binds the list items to a view
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.text.text = mList[position].text
        }

        override fun getItemCount() = mList.size
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}