package com.example.artwatch.Viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.artwatch.Data.EditArtistModel
import com.example.artwatch.Data.IncidentsModel
import com.example.artwatch.Data.ResponseModel
import com.example.artwatch.Retrofit.RetroInstance
import com.example.artwatch.Retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditArtistViewModel: ViewModel() {
    lateinit var createNewIncidentLiveData: MutableLiveData<ResponseModel?>
    init {
        createNewIncidentLiveData = MutableLiveData()
    }

    fun getCreateNewUserObserver(): MutableLiveData<ResponseModel?> {
        return createNewIncidentLiveData
    }


    fun createNewIncident(artistNewDetails: EditArtistModel) {
        val retroService  = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.editArtist(artistNewDetails)
        call.enqueue(object: Callback<ResponseModel> {
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                createNewIncidentLiveData.postValue(null)
            }

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if(response.isSuccessful) {
                    createNewIncidentLiveData.postValue(response.body())
                } else {
                    createNewIncidentLiveData.postValue(null)
                }
            }
        })
    }
}