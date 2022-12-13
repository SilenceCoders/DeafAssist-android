package com.mobileapp.deafassist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DeafAssistViewModel : ViewModel() {
    // Create a LiveData with a String
    val currentText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun update(newText: String) { currentText.value = newText}
    // Rest of the ViewModel...

}