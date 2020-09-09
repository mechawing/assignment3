package com.example.assignment3.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.assignment3.R
import com.example.assignment3.modules.NetworkProvider
import com.example.assignment3.modules.ViewModelFactoryProvider
import com.example.assignment3.model.MovieItemDetail
import com.example.assignment3.model.Network
import com.example.assignment3.viewmodel.CustomViewModel
import com.example.assignment3.viewmodel.CustomViewModelFactory

class MainActivity : AppCompatActivity(), OpenMovieDetail {

    private val network: Network by lazy { NetworkProvider().provideNetwork() }
    private val vmFactory: CustomViewModelFactory by lazy { ViewModelFactoryProvider()
        .providerViewModelFactory(network) }
    @Override
    override fun openMovieDetail(movieID: Int) {
        viewModel.initNetworkDetailMovie(movieID)
    }

    val viewModel: CustomViewModel by lazy {
        ViewModelProvider(this, vmFactory).get(CustomViewModel::class.java)
    }

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.getPopularItems().observe(
            this,
            Observer {
                initPopularList(PopularMovieList.newInstance(it, this))
            })
        viewModel.getMovieItem().observe(
            this,
            Observer { initMovieDetailView(it) }
        )
        viewModel.getErrorMessage().observe(
            this,
            Observer { errorMessage(it) }
        )

        viewModel.initNetworkPopularMovie()
    }

    @Override
    fun initPopularList(fragmentMovieList: PopularMovieList){
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.frameLayout,
                fragmentMovieList)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    @Override
    fun initMovieDetailView(data: MovieItemDetail){
        val fragment = MovieDetailView.newInstance(data)
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content,
                fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }
    @Override
    fun errorMessage(error: String) = Toast.makeText(this, error, Toast.LENGTH_LONG).show()
}
