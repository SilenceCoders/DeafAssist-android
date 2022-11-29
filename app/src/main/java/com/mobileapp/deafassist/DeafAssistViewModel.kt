package com.mobileapp.deafassist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DeafAssistViewModel : ViewModel() {
    // Create a LiveData with a String
    val currentText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    // Rest of the ViewModel...

}