package com.mobileapp.deafassist.favoriteList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.mobileapp.deafassist.data.FavoriteEntity
import com.mobileapp.deafassist.data.FavoritesApplication
import com.mobileapp.deafassist.databinding.FragmentItemListDialogListDialogBinding
import com.mobileapp.deafassist.databinding.FragmentItemListDialogListDialogItemBinding


// TODO: Customize parameter argument names
const val ARG_ITEM_COUNT = "item_count"

/**
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

    // ViewModel object
    val favoriteViewModel: FavoriteViewModel by  activityViewModels {
        FavoriteViewModelFactory((activity?.application as FavoritesApplication).repository)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemListDialogListDialogBinding.inflate(inflater, container, false)

        // Add vertical divider space between elements
        binding.list.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )

        // Connects our ViewModel with the database to receive the Flow of data. However, it does
        // not do anything with them (yet), since we will use them in the RecyclerView.
        favoriteViewModel.allFavorites.observe(this, Observer<List<FavoriteEntity>> {})

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = FavoriteAdapter(
            favoriteViewModel.allFavorites
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
    private inner class FavoriteAdapter internal constructor(private val mList: LiveData<List<FavoriteEntity>>) :
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
            holder.text.text = favoriteViewModel.allFavorites.value!![position].text
        }

        override fun getItemCount(): Int {
            return if (favoriteViewModel.allFavorites.value == null) 0 else
                favoriteViewModel.allFavorites.value!!.size
        }    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}