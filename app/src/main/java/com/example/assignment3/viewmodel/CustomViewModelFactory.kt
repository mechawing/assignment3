package com.example.assignment3.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.assignment3.model.Network

class CustomViewModelFactory(val network: Network?): ViewModelProvider.Factory {
    lateinit var viewModel: CustomViewModel

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return network?.let { CustomViewModel(it) } as T
    }
}