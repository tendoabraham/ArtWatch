package com.example.artwatch.Viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.artwatch.Data.ArtistsModel
import com.example.artwatch.Data.ResponseModel
import com.example.artwatch.Retrofit.RetroInstance
import com.example.artwatch.Retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FAQsViewModel: ViewModel() {
    lateinit var liveDataList: MutableLiveData<List<ArtistsModel>?>
    lateinit var liveDataResponse: MutableLiveData<ResponseModel>

    init {
        liveDataList = MutableLiveData()
        liveDataResponse = MutableLiveData()
    }


    fun getLiveDataObserver(): MutableLiveData<List<ArtistsModel>?> {
        return liveDataList
    }

    fun getLiveResponseObserver(): MutableLiveData<ResponseModel> {
        return liveDataResponse
    }

    fun makeAPICall() {
        val retroInstance = RetroInstance.getRetroInstance()
        val retroService  = retroInstance.create(RetroServiceInterface::class.java)
        val call  = retroService.getAllArtists()
        call.enqueue(object : Callback<List<ArtistsModel>> {
            override fun onFailure(call: Call<List<ArtistsModel>>, t: Throwable) {
                liveDataList.postValue(null)
            }

            override fun onResponse(
                call: Call<List<ArtistsModel>>,
                response: Response<List<ArtistsModel>>
            ) {
                liveDataList.postValue(response.body())
            }
        })


    }

    /*
    fun makeCreateArtistAPICall(artistsDetails: JSONObject) {
        val retroInstance = RetroInstance.getRetroInstance()
        val retroService  = retroInstance.create(RetroServiceInterface::class.java)
        val call  = retroService.addUser(artistsDetails)
        call.enqueue(object : Callback<ResponseModel> {
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                liveDataList.postValue(null)
                Log.e("Error", "Failure")
            }

            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) {
                liveDataResponse.postValue(response.body())
                Log.e("Response", liveDataResponse.value.toString())
            }

        })


    }*/
}