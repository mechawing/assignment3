package com.example.assignment3.modules

import com.example.assignment3.model.Network
import com.example.assignment3.viewmodel.CustomViewModelFactory

class ViewModelFactoryProvider {
    fun providerViewModelFactory(network: Network) = CustomViewModelFactory(network)
}