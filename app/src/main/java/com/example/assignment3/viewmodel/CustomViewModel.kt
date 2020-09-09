package com.example.assignment3.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment3.model.MovieItemDetail
import com.example.assignment3.model.MovieResponse
import com.example.assignment3.model.Network
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomViewModel(val network: Network) : ViewModel() {
    private  var dataSetPopularMovieList: MutableLiveData<MovieResponse> = MutableLiveData()
    private  var errorObservable: MutableLiveData<String> = MutableLiveData()
    private  var dataSetMovieDetail: MutableLiveData<MovieItemDetail> = MutableLiveData()

    fun getPopularItems() = dataSetPopularMovieList
    fun getMovieItem() = dataSetMovieDetail
    fun getErrorMessage() = errorObservable

    fun initNetworkPopularMovie() {
        network.apiResponse.getPopularMovies(api_Key = "0bb82b4229a8815f5d9e7410de8d8104").enqueue(object :
            Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                errorObservable.value = "Error:\n $t.message"
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                dataSetPopularMovieList.value = response.body()
            }
        })
    }

    fun nextPageNetworkPopularMovie(page: String) {
        network.apiResponse.getPopularMovies(page, "0bb82b4229a8815f5d9e7410de8d8104").enqueue(object :
            Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                errorObservable.value = "Error:\n $t.message"
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                dataSetPopularMovieList.value = response.body()
            }
        })
    }

    fun initNetworkDetailMovie(movieId: Int) {
        network.apiResponse.getMovieDetail(movieId, "0bb82b4229a8815f5d9e7410de8d8104").enqueue(object :
            Callback<MovieItemDetail> {
            override fun onFailure(call: Call<MovieItemDetail>, t: Throwable) {
                errorObservable.value = "Error:\n $t.message"
            }

            override fun onResponse(call: Call<MovieItemDetail>, response: Response<MovieItemDetail>) {
                dataSetMovieDetail.value= response.body()
            }
        })
    }
}