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

class CreateArtistViewModel: ViewModel() {
    lateinit var createNewUserLiveData: MutableLiveData<ResponseModel?>
    init {
        createNewUserLiveData = MutableLiveData()
    }

    fun getCreateNewUserObserver(): MutableLiveData<ResponseModel?> {
        return createNewUserLiveData
    }


    fun createNewUser(artistsDetails: ArtistsModel) {
        val retroService  = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.createArtist(artistsDetails)
        call.enqueue(object: Callback<ResponseModel> {
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                createNewUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if(response.isSuccessful) {
                    createNewUserLiveData.postValue(response.body())
                } else {
                    createNewUserLiveData.postValue(null)
                }
            }
        })
    }


}