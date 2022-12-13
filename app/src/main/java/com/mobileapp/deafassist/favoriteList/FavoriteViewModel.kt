package com.mobileapp.deafassist.favoriteList

import androidx.lifecycle.*
import com.mobileapp.deafassist.data.FavoriteEntity
import com.mobileapp.deafassist.data.FavoriteRepository
import kotlinx.coroutines.launch

data class FavoriteViewModel(private val repository: FavoriteRepository): ViewModel(){
    // Using LiveData and caching what allFavorites returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allFavorites: LiveData<List<FavoriteEntity>> = repository.allFavorites.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(favorite: FavoriteEntity) = viewModelScope.launch {
        repository.insert(favorite)
    }

}

class FavoriteViewModelFactory(private val repository: FavoriteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
