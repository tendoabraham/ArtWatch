package com.example.artwatch.Viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.artwatch.Data.LawyersModel
import com.example.artwatch.Data.ResponseModel
import com.example.artwatch.Retrofit.RetroInstance
import com.example.artwatch.Retrofit.RetroServiceInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LawyersViewModel : ViewModel() {
    lateinit var createNewLawyerrLiveData: MutableLiveData<ResponseModel?>
    init {
        createNewLawyerrLiveData = MutableLiveData()
    }

    fun getCreateNewLawyerObserver(): MutableLiveData<ResponseModel?> {
        return createNewLawyerrLiveData
    }


    fun createNewLawyer(lawyersDetails: LawyersModel) {
        val retroService  = RetroInstance.getLawyersRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.createLawyer(lawyersDetails)
        call.enqueue(object: Callback<ResponseModel> {
            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                createNewLawyerrLiveData.postValue(null)
            }

            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if(response.isSuccessful) {
                    createNewLawyerrLiveData.postValue(response.body())
                } else {
                    createNewLawyerrLiveData.postValue(null)
                }
            }
        })
    }


}