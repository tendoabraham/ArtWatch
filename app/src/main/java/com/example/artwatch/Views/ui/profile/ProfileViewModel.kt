package com.example.artwatch.Views.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.artwatch.Data.ArtistResponseModel
import com.example.artwatch.Data.IncidentsModel
import com.example.artwatch.Data.RequestModel
import com.example.artwatch.Data.ResponseModel
import com.example.artwatch.Retrofit.RetroInstance
import com.example.artwatch.Retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {
    var name: String = ""
    private val _text = MutableLiveData<String>().apply {
        value = name
    }
    val text: LiveData<String> = _text

    lateinit var getArtistLiveData: MutableLiveData<ArtistResponseModel?>
    init {
        getArtistLiveData = MutableLiveData()
    }

    fun getCreateNewUserObserver(): MutableLiveData<ArtistResponseModel?> {
        return getArtistLiveData
    }


    fun getArtist(artistID: RequestModel) {
        val retroService  = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.getArtist(artistID)
        call.enqueue(object: Callback<ArtistResponseModel> {
            override fun onFailure(call: Call<ArtistResponseModel>, t: Throwable) {
                getArtistLiveData.postValue(null)
            }

            override fun onResponse(call: Call<ArtistResponseModel>, response: Response<ArtistResponseModel>) {
                if(response.isSuccessful) {
                    getArtistLiveData.postValue(response.body())
                    Log.e("Response", response.body().toString())
                    var artistDetails: ArtistResponseModel? = response.body()
                    if (artistDetails != null) {
                        name = artistDetails.fullNames.toString()
                    }
                } else {
                    getArtistLiveData.postValue(null)
                }
            }
        })
    }
}